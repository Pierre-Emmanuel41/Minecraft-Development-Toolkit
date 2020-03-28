package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IExplanation;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IHelper;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IParentEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.managers.MessageManager;

public class Helper<T extends IExplanation, U> extends AbstractGenericEdition<T> implements IHelper<T, U> {
	private IParentEdition<T, U> parent;

	public Helper(T explanation) {
		super("help", explanation);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			Collection<IMapEdition<T, U, IParentEdition<T, U>>> values = parent.getChildren().values();
			IMapEdition<T, U, IParentEdition<T, U>> edition = parent.getChildren().get(args[0]);

			for (int i = 1; i < args.length; i++) {
				if (edition != null) {
					values = edition.getChildren().values();
					edition = edition.getChildren().get(args[i]);
				}
			}
			return filter(filter(values.stream()), args[args.length - 1]);
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			return emptyList();
		}
	}

	@Override
	public IHelper<T, U> setParent(IParentEdition<T, U> parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public void help(CommandSender sender, String[] args) {
		try {
			IMapEdition<T, U, IParentEdition<T, U>> edition = parent.getChildren().get(args[0]);
			for (int i = 1; i < args.length; i++)
				if (edition != null)
					edition = edition.getChildren().get(args[i]);
			sendMessage((Player) sender, edition.getExplanation());
		} catch (IndexOutOfBoundsException e) {
			sendMessage((Player) sender, parent.getExplanation());
		}
	}

	/**
	 * Send the given message to the specified player.
	 * 
	 * @param player  The player that receive the explanation.
	 * @param message The message to send.
	 * 
	 * @see MessageManager#sendMessage(Player, String)
	 */
	protected void sendMessage(Player player, String message) {
		MessageManager.sendMessage(player, message);
	}

	/**
	 * Send the given explanation to the specified sender.
	 * 
	 * @param player      The player that receive the explanation.
	 * @param explanation The explanation to send.
	 * 
	 * @see #sendMessage(Player, String)
	 */
	protected void sendMessage(Player player, T explanation) {
		sendMessage(player, explanation.get());
	}

	/**
	 * Before sending each explanation, they are concatenated into one string using a {@link StringJoiner} with delimiter "\n".
	 * 
	 * @param player      The player that receive the explanation.
	 * @param explanation The list of explanations to send.
	 * 
	 * @see #sendMessage(Player, String)
	 */
	protected void sendMessage(Player player, List<T> explanations) {
		StringJoiner joiner = new StringJoiner("\n");
		for (T explanation : explanations)
			joiner.add(explanation.get());
		sendMessage(player, joiner.toString());
	}

	private Stream<String> filter(Stream<IMapEdition<T, U, IParentEdition<T, U>>> stream) {
		return stream.filter(e -> e.isAvailable()).filter(e -> !e.getLabel().equals("help")).map(e -> e.getLabel());
	}
}
