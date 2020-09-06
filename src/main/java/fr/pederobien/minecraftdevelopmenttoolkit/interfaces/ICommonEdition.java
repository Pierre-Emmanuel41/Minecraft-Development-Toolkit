package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

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
public interface ICommonEdition<T, U extends IGenericEdition<T>, V> extends INodeEdition<T, U, V>, IAvailableEdition<V>, IModifiableEdition<V> {

}
