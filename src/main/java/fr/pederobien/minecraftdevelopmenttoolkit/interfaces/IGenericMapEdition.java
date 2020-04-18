package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of element to managed by its parent <code>V</code>.
 * @param <V> The type of the edition used to get the object to modify.
 * @param <W> The type of element to add to this node edition.
 * 
 * @see IManagedEdition
 * @see IAvailableEdition
 * @see IModifiableEdition
 * @see INodeEdition
 * @see CommandExecutor
 */
public interface IGenericMapEdition<T, U, V extends IManagedEdition<U>, W extends IGenericMapEdition<T, U, V, W>>
		extends IAvailableEdition<W>, IModifiableEdition<W>, INodeEdition<T, W, W>, CommandExecutor {

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
