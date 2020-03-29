package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

public interface IGenericParentEdition<T, U, V extends IManagedEdition<U>> extends IManagedEdition<U>, IAvailableEdition<IGenericParentEdition<T, U, V>>,
		IModifiableEdition<IGenericParentEdition<T, U, V>>, INodeEdition<T, IGenericMapEdition<T, U, V>, IGenericParentEdition<T, U, V>>, CommandExecutor {

	IGenericParentEdition<T, U, V> setHelper(IHelper<T, U, V> helper);
}
