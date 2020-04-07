package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;

public abstract class AbstractGenericParentEdition<T, U, V extends IManagedEdition<U>> extends AbstractGenericEdition<T> implements IGenericParentEdition<T, U, V> {
	private IHelper<T, U, V> helper;
	private boolean available, modifiable;
	private Map<String, IGenericMapEdition<T, U, V>> editions;

	public AbstractGenericParentEdition(String label, T explanation, IHelper<T, U, V> helper) {
		super(label, explanation);
		setHelper(helper);
		editions = new HashMap<String, IGenericMapEdition<T, U, V>>();
		available = true;
		modifiable = true;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public IGenericParentEdition<T, U, V> setAvailable(boolean available) {
		if (!modifiable)
			return this;
		this.available = available;
		for (IGenericMapEdition<T, U, V> edition : editions.values())
			edition.setAvailable(available);
		return this;
	}

	@Override
	public boolean isModifiable() {
		return modifiable;
	}

	@Override
	public IGenericParentEdition<T, U, V> setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
		return this;
	}

	@Override
	public IGenericParentEdition<T, U, V> addEdition(IGenericMapEdition<T, U, V> elt) {
		editions.put(elt.getLabel(), elt);
		return this;
	}

	@Override
	public IGenericParentEdition<T, U, V> removeEdition(IGenericMapEdition<T, U, V> elt) {
		editions.remove(elt.getLabel());
		return this;
	}

	@Override
	public Map<String, IGenericMapEdition<T, U, V>> getChildren() {
		return Collections.unmodifiableMap(editions);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!isAvailable())
			return emptyList();
		else {
			try {
				String label = args[0];
				IGenericMapEdition<T, U, V> edition = editions.get(label);

				// If the edition is available then execute its method onTabComplete otherwise return an empty list of String.
				if (edition != null)
					return edition.isAvailable() ? edition.onTabComplete(sender, command, alias, extract(args, 1)) : emptyList();

				// If the label correspond to "help" then execute its method onTabComplete.
				if (label.equals(helper.getLabel()))
					return helper.onTabComplete(sender, command, alias, extract(args, 1));

				Stream<String> availableLabels = editions.values().stream().filter(e -> e.isAvailable()).map(e -> e.getLabel());
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
		IGenericMapEdition<T, U, V> edition = editions.get(first);
		if (edition == null)
			throw new ArgumentNotFoundException(label, first, args);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), edition.getLabel());

		return edition.onCommand(sender, command, label, args);
	}

	@Override
	public IGenericParentEdition<T, U, V> setHelper(IHelper<T, U, V> helper) {
		this.helper = helper;
		helper.setParent(this);
		return this;
	}
}
