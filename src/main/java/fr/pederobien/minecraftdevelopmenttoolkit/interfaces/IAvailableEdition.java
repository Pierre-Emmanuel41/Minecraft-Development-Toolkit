package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

public interface IAvailableEdition<T> {

	/**
	 * @return True if this edition is available, false otherwise.
	 */
	boolean isAvailable();

	/**
	 * Set the availability of this edition.
	 * 
	 * @param available The new value that represents the availability of this edition.
	 * @return This edition as <code>T</code>
	 */
	T setAvailable(boolean available);
}
