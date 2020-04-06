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
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.managers.MessageManager;

public abstract class AbstractHelper<T, U, V extends IManagedEdition<U>> extends AbstractGenericEdition<T> implements IHelper<T, U, V> {
	private IGenericParentEdition<T, U, V> parent;

	public AbstractHelper(T explanation) {
		super("help", explanation);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			Collection<IGenericMapEdition<T, U, V>> values = parent.getChildren().values();
			IGenericMapEdition<T, U, V> edition = parent.getChildren().get(args[0]);

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
	public IHelper<T, U, V> setParent(IGenericParentEdition<T, U, V> parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public void help(CommandSender sender, String[] args) {
		try {
			IGenericMapEdition<T, U, V> edition = parent.getChildren().get(args[0]);
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

	private void sendMessage(Player player, IGenericParentEdition<T, U, V> parent) {
		sendExplanations(player, concat(parent, parent.getChildren()));
	}

	private void sendMessage(Player player, IGenericMapEdition<T, U, V> edition) {
		sendExplanations(player, concat(edition, edition.getChildren()));
	}

	private Stream<IGenericEdition<T>> concat(IGenericEdition<T> genericEdition, Map<String, IGenericMapEdition<T, U, V>> map) {
		return Stream.concat(Stream.of(genericEdition), map.values().stream().filter(e -> e.isAvailable()));
	}

	private void sendExplanations(Player player, Stream<IGenericEdition<T>> explanations) {
		sendMessage(player, explanations.collect(Collectors.toList()));
	}

	private Stream<String> filter(Stream<IGenericMapEdition<T, U, V>> stream) {
		return stream.filter(e -> e.isAvailable()).filter(e -> !e.getLabel().equals("help")).map(e -> e.getLabel());
	}
}
