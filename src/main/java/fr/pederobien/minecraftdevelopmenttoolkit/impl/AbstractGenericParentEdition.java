package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.ArgumentNotFoundException;
import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableCommandException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericParentEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IHelper;

public abstract class AbstractGenericParentEdition<T, U, V extends IGenericParentEdition<T, U, V, W>, W extends IGenericMapEdition<T, U, V, W>>
		extends AbstractGenericEdition<T> implements IGenericParentEdition<T, U, V, W> {
	private IHelper<T, U, V, W> helper;
	private boolean available, modifiable;
	private Map<String, W> editions;
	private List<W> descendants;

	public AbstractGenericParentEdition(String label, T explanation, IHelper<T, U, V, W> helper) {
		super(label, explanation);
		setHelper(helper);
		available = true;
		modifiable = true;
		editions = new HashMap<String, W>();
		descendants = new ArrayList<W>();
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public boolean isModifiable() {
		return modifiable;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!isAvailable())
			return emptyList();
		else {
			try {
				String label = args[0];
				W edition = getChildren().get(label);

				// If the edition is available then execute its method onTabComplete otherwise return an empty list of String.
				if (edition != null)
					return edition.isAvailable() ? edition.onTabComplete(sender, command, alias, extract(args, 1)) : emptyList();

				// If the label correspond to "help" then execute its method onTabComplete.
				if (label.equals(helper.getLabel()))
					return helper.onTabComplete(sender, command, alias, extract(args, 1));

				Stream<String> availableLabels = getChildren().values().stream().filter(e -> e.isAvailable()).map(e -> e.getLabel());
				Stream<String> labels = Stream.concat(Stream.of(helper.getLabel()), availableLabels);
				return filter(labels, label);
			} catch (IndexOutOfBoundsException e) {
				return emptyList();
			}
		}
	}

	/**
	 * @throws CommandNotFoundException If the command to execute has not been found
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String first = "";
		if (!isAvailable())
			throw new NotAvailableCommandException(command.getLabel());

		// If the label correspond to "help" then execute its method help.
		first = args[0];
		if (first.equals(helper.getLabel())) {
			if (sender instanceof Player)
				helper.help(sender, extract(args, 1));
			return true;
		}

		// If the edition is available then execute its method onCommand.
		W edition = getChildren().get(first);
		if (edition == null)
			throw new ArgumentNotFoundException(label, first, args);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), edition.getLabel());

		return edition.onCommand(sender, command, label, extract(args, 1));
	}

	@Override
	public IGenericParentEdition<T, U, V, W> setHelper(IHelper<T, U, V, W> helper) {
		this.helper = helper;
		helper.setParent(this);
		return this;
	}

	@Override
	public Map<String, W> getChildren() {
		return Collections.unmodifiableMap(editions);
	}

	@Override
	public List<W> getChildrenByLabelName(String labelName) {
		return descendants.stream().filter(edition -> edition.getLabel().equals(labelName)).collect(Collectors.toList());
	}

	protected void internalSetAvailable(boolean available) {
		if (!modifiable)
			return;
		this.available = available;
		for (W edition : getChildren().values())
			edition.setAvailable(available);
	}

	protected void internalSetModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	protected void internalAdd(W elt) {
		internalAddToDescendants(elt);
		editions.put(elt.getLabel(), elt);
	}

	protected void internalRemove(W elt) {
		internalRemoveFromDescendants(elt);
		editions.remove(elt.getLabel());
	}

	private void internalAddToDescendants(W elt) {
		for (W element : elt.getChildren().values())
			internalAddToDescendants(element);
		descendants.add(elt);
	}

	private void internalRemoveFromDescendants(W elt) {
		for (W element : elt.getChildren().values())
			internalRemoveFromDescendants(element);
		descendants.remove(elt);
	}
}
