package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericCommonMapEdition;

public abstract class AbstractGenericCommonMapEdition<T, U extends IGenericCommonMapEdition<T, U>> extends AbstractCommonEdition<T, U, U>
		implements IGenericCommonMapEdition<T, U> {
	private List<U> descendants;

	public AbstractGenericCommonMapEdition(String label, T explanation) {
		super(label, explanation);
		descendants = new ArrayList<U>();
	}

	@Override
	public List<U> getChildrenByLabelName(String labelName) {
		return descendants.stream().filter(edition -> edition.getLabel().equals(labelName)).collect(Collectors.toList());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			U edition = getChildren().get(args[0]);

			// Edition not recognized, display all available children editions.
			if (edition == null)
				return filter(getChildren().values().stream().filter(e -> e.isAvailable()).map(e -> e.getLabel()), args[0]);

			// Return an empty list if there are no edition corresponding to the given args[0] parameter.
			if (!edition.isAvailable())
				return emptyList();

			return edition.onTabComplete(sender, command, alias, extract(args, 1));
		} catch (IndexOutOfBoundsException e) {
			// When args is empty -> args[0] throw an IndexOutOfBoundsException
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String editionLabel = args[0];
		U edition = getChildren().get(editionLabel);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), editionLabel);

		edition.onCommand(sender, command, label, extract(args, 1));
		return true;
	}

	protected void internalSetAvailable(boolean available) {
		super.internalSetAvailable(available);
		if (isModifiable())
			for (U edition : getChildren().values())
				edition.setAvailable(available);
	}

	protected void internalAdd(U elt) {
		super.internalAdd(elt);
		internalAddToDescendants(elt);
	}

	protected void internalRemove(U elt) {
		super.internalRemove(elt);
		internalRemoveFromDescendants(elt);
	}

	private void internalAddToDescendants(U elt) {
		for (U element : elt.getChildren().values())
			internalAddToDescendants(element);
		descendants.add(elt);
	}

	private void internalRemoveFromDescendants(U elt) {
		for (U element : elt.getChildren().values())
			internalRemoveFromDescendants(element);
		descendants.remove(elt);
	}
}
