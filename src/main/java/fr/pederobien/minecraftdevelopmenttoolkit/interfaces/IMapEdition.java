package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of element to add to this map edition.
 * @param <V> The type of the edition used to get the object to modify.
 * 
 * @see IManagedEdition
 * @see IAvailableEdition
 * @see IModifiableEdition
 * @see INodeEdition
 * @see CommandExecutor
 */
public interface IMapEdition<T extends IExplanation, U, V extends IManagedEdition<U>> extends IAvailableEdition<IMapEdition<T, U, V>>,
		IModifiableEdition<IMapEdition<T, U, V>>, INodeEdition<T, IMapEdition<T, U, V>, IMapEdition<T, U, V>>, CommandExecutor {

	/**
	 * Set the parent of this edition. This parent contains the object managed by this edition and all inherited interfaces/classes.
	 * 
	 * @param parent The parent that contains the object to modify by this edition.
	 */
	void setParent(V parent);

	/**
	 * @return the parent of this edition that contains the object to modify.
	 */
	V getParent();
}
