package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import org.bukkit.GameRule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.dictionary.interfaces.IDictionaryManager;
import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeMapEdition;
import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageEvent;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftNotificationCenter;
import fr.pederobien.minecraftmanagers.WorldManager;

public abstract class AbstractMessageCodeMapEdition<U, V extends IManagedEdition<U>, W extends IMessageCodeMapEdition<U, V, W>>
		extends AbstractGenericMapEdition<IMinecraftMessageCode, U, V, W> implements IMessageCodeMapEdition<U, V, W> {

	public AbstractMessageCodeMapEdition(String label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}

	/**
	 * @return The notification center to send message to player(s) that are currently logged into the server.
	 */
	protected abstract IMinecraftNotificationCenter getNotificationCenter();

	/**
	 * Send a message to the given player. First create an {@link IMinecraftMessageEvent} that is used to get messages into registered
	 * dictionaries for the given Plugin. This method is synchronized with {@link GameRule#SEND_COMMAND_FEEDBACK}. This means that if
	 * the game rule has been set to false, then no message is send.
	 * 
	 * @param sender Generally a player, it is used to get a message in his language.
	 * @param plugin The plugin into the message is associated.
	 * @param code   The code used to get the translation of the message in the player's language.
	 * @param args   Arguments that could be useful to send dynamic messages.
	 * 
	 * @return The created message event.
	 */
	protected void sendMessageToSender(CommandSender sender, IMinecraftMessageCode code, Object... args) {
		if (sender instanceof Player && WorldManager.OVERWORLD.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK))
			getNotificationCenter().sendMessage(messageEvent((Player) sender, code, args));
	}

	/**
	 * Send a message to the given player. First create an {@link IMinecraftMessageEvent} that is used to get messages into registered
	 * dictionaries for the given Plugin. This method send a message even if the game rule {@link GameRule#SEND_COMMAND_FEEDBACK} is
	 * set to false.
	 * 
	 * @param sender Generally a player, it is used to get a message in his language.
	 * @param plugin The plugin into the message is associated.
	 * @param code   The code used to get the translation of the message in the player's language.
	 * @param args   Arguments that could be useful to send dynamic messages.
	 * 
	 * @return The created message event.
	 * 
	 * @see #sendMessageToSender(CommandSender, IMinecraftMessageCode, Object...)
	 */
	protected void sendMessageToSenderNotSynchonized(CommandSender sender, IMinecraftMessageCode code, Object... args) {
		if (sender instanceof Player && WorldManager.OVERWORLD.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK))
			getNotificationCenter().sendMessage(messageEvent((Player) sender, code, args));
	}

	/**
	 * Get a message corresponding to the given message code.
	 * 
	 * @param sender Generally a player, it is used to get a message in his language.
	 * @param code   The code used to get the translation of the message in the player's language.
	 * @param args   Arguments that could be useful to send dynamic messages.
	 * 
	 * @return The message associated to the specified code. If the sender is not a player, then it returns an empty string.
	 */
	protected String getMessageFromDictionary(CommandSender sender, IMinecraftMessageCode code, Object... args) {
		return sender instanceof Player ? getMessage((Player) sender, code, args) : "";
	}

	private IMinecraftMessageEvent messageEvent(Player player, IMinecraftMessageCode code, Object... args) {
		String[] internalArgs = new String[args.length];
		for (int i = 0; i < args.length; i++)
			internalArgs[i] = args[i].toString();
		return new MinecraftMessageEvent(player, code, (Object[]) internalArgs);
	}

	private String getMessage(Player player, IMinecraftMessageCode code, Object... args) {
		return ((IDictionaryManager) getNotificationCenter().getDictionaryContext()).getMessage(messageEvent(player, code, args));
	}
}
