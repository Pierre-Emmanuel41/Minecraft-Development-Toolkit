package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import java.util.Map;

public interface INodeEdition<T, U extends IGenericEdition<T>, V> extends IGenericEdition<T> {

	/**
	 * Add element to this node edition. This element is stored into a Map with key is {@link IGenericEdition#getLabel()} and the
	 * value is itself.
	 * 
	 * @param elt The element to add.
	 * @return This edition as <code>T</code>
	 */
	V addEdition(U elt);

	/**
	 * Remove the element from this node edition.
	 * 
	 * @param elt The element to remove.
	 * @return This edition as <code>T</code>
	 */
	V removeEdition(U elt);

	/**
	 * @return An unmodifiable view as map of all children of this node edition.
	 */
	Map<String, U> getChildren();
}
