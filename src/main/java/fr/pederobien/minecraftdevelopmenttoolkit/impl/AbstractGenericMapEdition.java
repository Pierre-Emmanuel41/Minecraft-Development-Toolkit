package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;

public class AbstractGenericMapEdition<T, U, V extends IManagedEdition<U>> extends AbstractGenericEdition<T> implements IGenericMapEdition<T, U, V> {
	private V parent;
	private boolean available, modifiable;
	private Map<String, IGenericMapEdition<T, U, V>> editions;

	public AbstractGenericMapEdition(String label, T explanation) {
		super(label, explanation);
		editions = new HashMap<String, IGenericMapEdition<T, U, V>>();
		available = true;
		modifiable = true;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public IGenericMapEdition<T, U, V> setAvailable(boolean available) {
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
	public IGenericMapEdition<T, U, V> setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
		return this;
	}

	@Override
	public IGenericMapEdition<T, U, V> addEdition(IGenericMapEdition<T, U, V> elt) {
		editions.put(elt.getLabel(), elt);
		return this;
	}

	@Override
	public IGenericMapEdition<T, U, V> removeEdition(IGenericMapEdition<T, U, V> elt) {
		editions.remove(elt.getLabel());
		return this;
	}

	@Override
	public Map<String, IGenericMapEdition<T, U, V>> getChildren() {
		return Collections.unmodifiableMap(editions);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			IGenericMapEdition<T, U, V> edition = editions.get(args[0]);

			// Edition not recognised, display all available children editions.
			if (edition == null)
				return filter(editions.values().stream().filter(e -> e.isAvailable()).map(e -> e.getLabel()), args[0]);

			// Return an empty list if there are no edition corresponding to the given args[0] parameter.
			if (!edition.isAvailable())
				return emptyList();

			return edition.onTabComplete(sender, command, alias, extract(args, 1));
		} catch (IndexOutOfBoundsException e) {

			// When args is empty -> args[0] throw an IndexOutOfBoundsException
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String editionLabel = args[0];
		IGenericMapEdition<T, U, V> edition = editions.get(editionLabel);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), editionLabel);

		edition.onCommand(sender, command, label, extract(args, 1));
		return true;
	}

	@Override
	public void setParent(V parent) {
		this.parent = parent;
		for (IGenericMapEdition<T, U, V> edition : editions.values())
			edition.setParent(parent);
	}

	@Override
	public V getParent() {
		return parent;
	}

	/**
	 * Just to reduce line size, this method is equivalent to <code>getParent().get()</code>
	 * 
	 * @return The object to modify.
	 */
	protected U get() {
		return getParent().get();
	}

	/**
	 * Check if the element verify the rules coming from the given predicate. If the element verify the rules, then it returns the
	 * specified list of String. Otherwise, it return an empty list of String.
	 * 
	 * @param element      The element to check.
	 * @param predicate    The predicate that contains the rules.
	 * @param returnedList The list to return if the element verify the rules.
	 * 
	 * @return A List of String.
	 */
	protected List<String> check(String element, Predicate<String> predicate, List<String> returnedList) {
		return predicate.test(element) ? returnedList : emptyList();
	}
}
