package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;

public abstract class AbstractGenericMapEdition<T, U, V extends IManagedEdition<U>, W extends IGenericMapEdition<T, U, V, W>> extends AbstractGenericEdition<T>
		implements IGenericMapEdition<T, U, V, W> {
	private V parent;
	private boolean available, modifiable;
	private Map<String, W> editions;
	private List<W> descendants;

	public AbstractGenericMapEdition(String label, T explanation) {
		super(label, explanation);
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
		try {
			W edition = getChildren().get(args[0]);

			// Edition not recognised, display all available children editions.
			if (edition == null)
				return filter(getChildren().values().stream().filter(e -> e.isAvailable()).map(e -> e.getLabel()), args[0]);

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
		W edition = getChildren().get(editionLabel);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), editionLabel);

		edition.onCommand(sender, command, label, extract(args, 1));
		return true;
	}

	@Override
	public void setParent(V parent) {
		this.parent = parent;
		for (W edition : getChildren().values())
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
