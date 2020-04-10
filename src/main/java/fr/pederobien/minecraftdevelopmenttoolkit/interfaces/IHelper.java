package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandSender;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of the object managed by {@link IManagedEdition}.
 * @param <V> The type of the edition used to get the object to modify.
 * @param <W> The type of element to add to this node edition.
 */
public interface IHelper<T, U, V extends IGenericParentEdition<T, U, V, W>, W extends IGenericMapEdition<T, U, V, W>> extends IGenericEdition<T> {

	/**
	 * Set the parent used to show the explanation of each children generic edition.
	 * 
	 * @param parent The parent that contains generic edition.
	 * @return This helper.
	 */
	IHelper<T, U, V, W> setParent(IGenericParentEdition<T, U, V, W> parent);

	/**
	 * Send to the given command sender the explanation of each generic edition of the parent.
	 * 
	 * @param sender The object used to send explanations.
	 * @param args   An array used to send the explanation of one specific generic edition or the explanation of each generic edition.
	 * 
	 * @see #setParent(IGenericParentEdition)
	 */
	void help(CommandSender sender, String[] args);

}
