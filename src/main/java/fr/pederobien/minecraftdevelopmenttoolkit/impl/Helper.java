package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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

public abstract class Helper<T, U, V extends IManagedEdition<U>> extends AbstractGenericEdition<T> implements IHelper<T, U, V> {
	private IGenericParentEdition<T, U, V> parent;

	public Helper(T explanation) {
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
	 * Before sending each explanation, they are concatenated into one string using a {@link StringJoiner} with delimiter "\n".
	 * 
	 * @param player      The player that receive the explanation.
	 * @param explanation The list of explanations to send.
	 * 
	 * @see #sendMessage(Player, String)
	 */
	protected abstract void sendMessage(Player player, List<T> explanations);

	private void sendMessage(Player player, IGenericParentEdition<T, U, V> parent) {
		sendExplanations(player, concat(parent, parent.getChildren()));
	}

	private void sendMessage(Player player, IGenericMapEdition<T, U, V> edition) {
		sendExplanations(player, concat(edition, edition.getChildren()));
	}

	private Stream<T> concat(IGenericEdition<T> genericEdition, Map<String, IGenericMapEdition<T, U, V>> map) {
		return Stream.concat(Stream.of(genericEdition.getExplanation()), map.values().stream().filter(e -> e.isAvailable()).map(e -> e.getExplanation()));
	}

	private void sendExplanations(Player player, Stream<T> explanations) {
		sendMessage(player, explanations.collect(Collectors.toList()));
	}

	private Stream<String> filter(Stream<IGenericMapEdition<T, U, V>> stream) {
		return stream.filter(e -> e.isAvailable()).filter(e -> !e.getLabel().equals("help")).map(e -> e.getLabel());
	}
}
