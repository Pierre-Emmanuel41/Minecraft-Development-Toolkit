package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

import org.bukkit.command.CommandSender;

/**
 * 
 * @author Pierre-Emmanuel
 *
 * @param <T> The type of the explanation.
 * @param <U> The type of the object managed by {@link IManagedEdition}.
 */
public interface IHelper<T, U> extends IGenericEdition<T> {

	/**
	 * Set the parent used to show the explanation of each children generic edition.
	 * 
	 * @param parent The parent that contains generic edition.
	 * @return This helper.
	 */
	IHelper<T, U> setParent(IParentEdition<T, U> parent);

	/**
	 * Send to the given command sender the explanation of each generic edition of the parent.
	 * 
	 * @param sender The object used to send explanations.
	 * @param args   An array used to send the explanation of one specific generic edition or the explanation of each generic edition.
	 * 
	 * @see #setParent(IParentEdition)
	 */
	void help(CommandSender sender, String[] args);

}
