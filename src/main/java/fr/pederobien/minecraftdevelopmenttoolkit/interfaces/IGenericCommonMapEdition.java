package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandExecutor;

public interface IGenericCommonMapEdition<T, U extends IGenericCommonMapEdition<T, U>> extends ICommonEdition<T, U, U>, CommandExecutor {

}
