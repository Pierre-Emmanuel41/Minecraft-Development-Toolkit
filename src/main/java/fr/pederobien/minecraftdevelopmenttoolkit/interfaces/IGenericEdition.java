package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.TabCompleter;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * 
 * @see TabCompleter
 * @see Comparable
 */
public interface IGenericEdition<T> extends TabCompleter, Comparable<IGenericEdition<T>> {

	/**
	 * @return The label of this edition. It is shown as minecraft command argument.
	 */
	String getLabel();

	/**
	 * @param T A generic parameter used to get different type of explanation. In most case, the explanation is a String. But it could
	 *          append that developers need more than a simple String.
	 * @return An explanation used to explain what this argument does for the main command.
	 */
	T getExplanation();
}
