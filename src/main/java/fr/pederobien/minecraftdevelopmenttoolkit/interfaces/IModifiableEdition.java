package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <V> The type in which this edition is returned.
 */
public interface IModifiableEdition<T> {

	/**
	 * @return True if this edition is modifiable, false otherwise.
	 */
	boolean isModifiable();

	/**
	 * When this edition is not modifiable, the all setters are inactive (except this setter).
	 * 
	 * @param modifiable The new value that represents the modifiability of this edition.
	 * @return This edition as <code>T</code>
	 */
	T setModifiable(boolean modifiable);
}
