package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.INodeEdition;

public abstract class AbstractGenericMapEdition<T, U, V extends IManagedEdition<U> & INodeEdition<T, W, V>, W extends IGenericMapEdition<T, U, V, W>>
		extends AbstractCommonEdition<T, W, W> implements IGenericMapEdition<T, U, V, W> {
	private V parent;
	private List<W> descendants;

	public AbstractGenericMapEdition(String label, T explanation) {
		super(label, explanation);
		descendants = new ArrayList<W>();
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

	@Override
	public List<W> getChildrenByLabelName(String labelName) {
		return descendants.stream().filter(edition -> edition.getLabel().equals(labelName)).collect(Collectors.toList());
	}

	protected void internalSetAvailable(boolean available) {
		super.internalSetAvailable(available);
		if (isModifiable())
			for (W edition : getChildren().values())
				edition.setAvailable(available);
	}

	protected void internalAdd(W elt) {
		super.internalAdd(elt);
		internalAddToDescendants(elt);
	}

	protected void internalRemove(W elt) {
		super.internalRemove(elt);
		internalRemoveFromDescendants(elt);
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
	 * Find all descendants of the parent whose label match on the given label and set their availability to true.
	 * 
	 * @param label The name of the label to match on.
	 * 
	 * @see IParentPersistenceEdition#getChildrenByLabelName(String)
	 * @see #setAvailable(boolean)
	 */
	protected void setAvailableEdition(String label) {
		getParent().getChildrenByLabelName(label).forEach(descendant -> descendant.setAvailable(true));
	}

	/**
	 * Find all descendants of the parent for each label in the given array and set their availability to true.
	 * 
	 * @param labels An array to find different parent's descendants.
	 * 
	 * @see AbstractMapPersistenceEdition#setAvailableEdition(String)
	 */
	protected void setAvailableEditions(String... labels) {
		for (String label : labels)
			setAvailableEdition(label);
	}

	/**
	 * Find all descendants of the parent whose label match on the given label and set their availability to true.
	 * 
	 * @param label The name of the label to match on.
	 * 
	 * @see IParentPersistenceEdition#getChildrenByLabelName(String)
	 * @see #setAvailable(boolean)
	 */
	protected void setNotAvailableEdition(String label) {
		getParent().getChildrenByLabelName(label).forEach(descendant -> descendant.setModifiable(true).setAvailable(true).setModifiable(false));
	}

	/**
	 * Find all descendants of the parent for each label in the given array and set their availability to true.
	 * 
	 * @param labels An array to find different parent's descendants.
	 * 
	 * @see AbstractMapPersistenceEdition#setAvailableEdition(String)
	 */
	protected void setNotAvailableEditions(String... labels) {
		for (String label : labels)
			setNotAvailableEdition(label);
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
