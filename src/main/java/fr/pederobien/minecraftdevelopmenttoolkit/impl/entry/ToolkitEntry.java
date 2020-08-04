package fr.pederobien.minecraftdevelopmenttoolkit.impl.entry;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftNotificationCenter;
import fr.pederobien.minecraftscoreboards.impl.AbstractEntry;

public abstract class ToolkitEntry extends AbstractEntry {
	private String before, after;

	/**
	 * Constructs an entry that propose methods to display player language sensitive messages.
	 * 
	 * @param score The line number of this entry.
	 */
	protected ToolkitEntry(int score) {
		super(score);
	}

	/**
	 * @return The notification center used to get a message associated to a {@link IMinecraftMessageCode} to be displayed in player
	 *         score board.
	 */
	protected abstract IMinecraftNotificationCenter getNotificationCenter();

	/**
	 * If not overridden, it is possible to furnish a code used to display a language sensitive information using
	 * {@link #getBeforeAsCode(Player)}.
	 */
	@Override
	public String getBefore() {
		IMinecraftMessageCode code = getBeforeAsCode(getPlayer());
		if (code == null)
			return "";
		if (before == null || !isBeforeConstant())
			before = getNotificationCenter().getMessage(new MinecraftMessageEvent(getPlayer(), code));
		return before;
	}

	/**
	 * If not overridden, it is possible to furnish a code used to display a language sensitive information using
	 * {@link #getAfterAsCode(Player)}.
	 */
	@Override
	public String getAfter() {
		IMinecraftMessageCode code = getAfterAsCode(getPlayer());
		if (code == null)
			return "";
		if (after == null || !isAfterConstant())
			after = getNotificationCenter().getMessage(new MinecraftMessageEvent(getPlayer(), code));
		return after;
	}

	/**
	 * Because method {@link #getBefore(Player)} is overridden by this class to propose some language sensitive entry, this method
	 * allow you to give a code corresponding to the translated message to display on player screen. The message associated to the
	 * returned code is displayed before the value to update. If not overridden, This method returns null. This means that the
	 * sequence of character displayed before the value to update is "";
	 * 
	 * @param player The player whose score board is updated.
	 * @return A minecraft message code used to display messages in player language.
	 * 
	 * @see #isAfterConstant() To precise if the message should not be updated at each entry update.
	 */
	protected IMinecraftMessageCode getBeforeAsCode(Player player) {
		return null;
	}

	/**
	 * Because method {@link #getAfter(Player)} is overridden by this class to propose some language sensitive entry, this method
	 * allow you to give a code corresponding to the translated message to display on player screen. The message associated to the
	 * returned code is displayed after the value to update. If not overridden, This method returns null. This means that the sequence
	 * of character displayed after the value to update is "";
	 * 
	 * @param player The player whose score board is updated.
	 * @return A minecraft message code used to display messages in player language.
	 * 
	 * @see #isAfterConstant() To precise if the message should not be updated at each entry update.
	 */
	protected IMinecraftMessageCode getAfterAsCode(Player player) {
		return null;
	}

	/**
	 * Method used to know if when this entry is updated, the message associated to the code returned by method
	 * {@link #getBeforeAsCode(Player)} should be also updated. In other words, if this method returns true, then this entry search in
	 * registered dictionaries the message associated to the code.
	 * 
	 * @return True if this entry search in registered dictionaries when update, false otherwise.
	 */
	protected boolean isBeforeConstant() {
		return true;
	}

	/**
	 * Method used to know if when this entry is updated, the message associated to the code returned by method
	 * {@link #getAfterAsCode(Player)} should be also updated. In other words, if this method returns true, then this entry search in
	 * registered dictionaries the message associated to the code.
	 * 
	 * @return True if this entry search in registered dictionaries when update, false otherwise.
	 */
	protected boolean isAfterConstant() {
		return true;
	}
}
