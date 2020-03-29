package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

public interface IParentEdition<T, U> extends IManagedEdition<U>, IAvailableEdition<IParentEdition<T, U>>, IModifiableEdition<IParentEdition<T, U>>,
		INodeEdition<T, IMapEdition<T, U, IParentEdition<T, U>>, IParentEdition<T, U>>, CommandExecutor {

	IParentEdition<T, U> setHelper(IHelper<T, U> helper);
}
