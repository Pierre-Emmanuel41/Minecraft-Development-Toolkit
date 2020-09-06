package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of element managed by this edition edition {@link IManagedEdition}.
 * @param <V> The type of the edition used to get the object to modify.
 * @param <W> The type of element to add to this node edition.
 */
public interface IGenericParentEdition<T, U, V extends IGenericParentEdition<T, U, V, W>, W extends IGenericMapEdition<T, U, V, W>>
		extends IManagedEdition<U>, ICommonEdition<T, W, V>, CommandExecutor {

	IGenericParentEdition<T, U, V, W> setHelper(IHelper<T, U, V, W> helper);
}
