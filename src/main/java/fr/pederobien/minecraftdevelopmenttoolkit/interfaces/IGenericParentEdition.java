package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

public interface IGenericParentEdition<T, U> extends IManagedEdition<U>, IAvailableEdition<IGenericParentEdition<T, U>>, IModifiableEdition<IGenericParentEdition<T, U>>,
		INodeEdition<T, IGenericMapEdition<T, U, IGenericParentEdition<T, U>>, IGenericParentEdition<T, U>>, CommandExecutor {

	IGenericParentEdition<T, U> setHelper(IHelper<T, U> helper);
}
