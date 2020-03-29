package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableEditionException;
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
		for (Map.Entry<String, IGenericMapEdition<T, U, V>> edition : editions.entrySet())
			edition.getValue().setAvailable(available);
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

				if (edition != null)
					return edition.isAvailable() ? edition.onTabComplete(sender, command, alias, extract(args, 1)) : emptyList();

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

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String first = "";
		if (!isAvailable())
			throw new NotAvailableEditionException(label);

		first = args[0];
		if (first.equals(helper.getLabel())) {
			helper.help(sender, extract(args, 1));
			return true;
		}

		IGenericMapEdition<T, U, V> edition = editions.get(first);
		if (edition.isAvailable()) {
			edition.onCommand(sender, command, label, extract(args, 1));
			return true;
		}
		return false;
	}

	@Override
	public IGenericParentEdition<T, U, V> setHelper(IHelper<T, U, V> helper) {
		this.helper = helper;
		helper.setParent(this);
		return this;
	}
}
