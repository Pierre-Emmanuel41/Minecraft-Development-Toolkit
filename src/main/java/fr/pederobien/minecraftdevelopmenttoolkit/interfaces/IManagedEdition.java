package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the object to modify.
 */
public interface IManagedEdition<T> {

	/**
	 * @return The object managed by this parent edition
	 */
	T get();

	/**
	 * Set the element managed by this parent edition.
	 * 
	 * @param element The new element managed by this edition.
	 */
	void set(T element);
}
