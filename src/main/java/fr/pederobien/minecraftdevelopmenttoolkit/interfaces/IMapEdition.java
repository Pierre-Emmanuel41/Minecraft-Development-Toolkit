package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

public interface IMapEdition<T, U, V extends IParentEdition<T>> extends IAvailableEdition<IMapEdition<T, U, V>>, IModifiableEdition<IMapEdition<T, U, V>>,
		INodeEdition<T, IMapEdition<T, U, V>, IMapEdition<T, U, V>>, CommandExecutor {

	/**
	 * Set the parent of this edition. This parent contains the object managed by this edition and all inherited interfaces/classes.
	 * 
	 * @param parent The parent that contains the object to modify by this edition.
	 */
	void setParent(V parent);
}
