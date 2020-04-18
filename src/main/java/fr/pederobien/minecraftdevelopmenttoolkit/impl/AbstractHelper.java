package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericParentEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IHelper;
import fr.pederobien.minecraftmanagers.MessageManager;

public abstract class AbstractHelper<T, U, V extends IGenericParentEdition<T, U, V, W>, W extends IGenericMapEdition<T, U, V, W>> extends AbstractGenericEdition<T>
		implements IHelper<T, U, V, W> {
	private IGenericParentEdition<T, U, V, W> parent;

	public AbstractHelper(T explanation) {
		super("help", explanation);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			Collection<W> values = parent.getChildren().values();
			W edition = parent.getChildren().get(args[0]);

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
	public IHelper<T, U, V, W> setParent(IGenericParentEdition<T, U, V, W> parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public void help(CommandSender sender, String[] args) {
		try {
			W edition = parent.getChildren().get(args[0]);
			for (int i = 1; i < args.length; i++)
				if (edition != null)
					edition = edition.getChildren().get(args[i]);
			sendMessage((Player) sender, edition);
		} catch (IndexOutOfBoundsException e) {
			sendMessage((Player) sender, parent);
		}
	}

	/**
	 * Send the specified message to the given player.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message to send to the player.
	 * 
	 * @see MessageManager#sendMessage(Player, String)
	 */
	protected void sendMessage(Player player, String message) {
		MessageManager.sendMessage(player, message);
	}

	/**
	 * Send the explanations of each editions from the given list of generic edition.
	 * 
	 * @param player   The player that receive the explanation.
	 * @param editions The list of editions to send their explanations.
	 */
	protected abstract void sendMessage(Player player, List<IGenericEdition<T>> editions);

	private void sendMessage(Player player, IGenericParentEdition<T, U, V, W> parent) {
		sendExplanations(player, concat(parent, parent.getChildren()));
	}

	private void sendMessage(Player player, W edition) {
		sendExplanations(player, concat(edition, edition.getChildren()));
	}

	private Stream<IGenericEdition<T>> concat(IGenericEdition<T> genericEdition, Map<String, W> map) {
		return Stream.concat(Stream.of(genericEdition), map.values().stream().filter(e -> e.isAvailable()));
	}

	private void sendExplanations(Player player, Stream<IGenericEdition<T>> explanations) {
		sendMessage(player, explanations.collect(Collectors.toList()));
	}

	private Stream<String> filter(Stream<W> stream) {
		return stream.filter(e -> e.isAvailable()).filter(e -> !e.getLabel().equals("help")).map(e -> e.getLabel());
	}
}
