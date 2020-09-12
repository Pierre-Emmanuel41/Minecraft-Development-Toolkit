package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.INodeEdition;

public abstract class AbstractGenericMapEdition<T, U, V extends IManagedEdition<U> & INodeEdition<T, W, V>, W extends IGenericMapEdition<T, U, V, W>>
		extends AbstractGenericCommonMapEdition<T, W> implements IGenericMapEdition<T, U, V, W> {
	private V parent;

	public AbstractGenericMapEdition(String label, T explanation) {
		super(label, explanation);
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
}
