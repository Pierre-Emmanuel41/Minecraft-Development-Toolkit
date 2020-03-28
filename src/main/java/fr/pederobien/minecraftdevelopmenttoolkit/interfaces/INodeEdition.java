package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import java.util.Map;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of element to add to this node edition.
 * @param <V> The type in which this edition is returned.
 * 
 * @see IGenericEdition
 */
public interface INodeEdition<T extends IExplanation, U extends IGenericEdition<T>, V> extends IGenericEdition<T> {

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
