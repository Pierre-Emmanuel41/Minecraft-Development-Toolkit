package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.NotAvailableArgumentException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;

public abstract class AbstractGenericMapEdition<T, U, V extends IManagedEdition<U>, W extends IGenericMapEdition<T, U, V, W>> extends AbstractGenericEdition<T>
		implements IGenericMapEdition<T, U, V, W> {
	private V parent;
	private boolean available, modifiable;
	private Map<String, W> editions;
	private List<W> descendants;

	public AbstractGenericMapEdition(String label, T explanation) {
		super(label, explanation);
		available = true;
		modifiable = true;
		editions = new HashMap<String, W>();
		descendants = new ArrayList<W>();
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public boolean isModifiable() {
		return modifiable;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			W edition = getChildren().get(args[0]);

			// Edition not recognised, display all available children editions.
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
		W edition = getChildren().get(editionLabel);

		if (!edition.isAvailable())
			throw new NotAvailableArgumentException(command.getLabel(), editionLabel);

		edition.onCommand(sender, command, label, extract(args, 1));
		return true;
	}

	@Override
	public void setParent(V parent) {
		this.parent = parent;
		for (W edition : getChildren().values())
			edition.setParent(parent);
	}

	@Override
	public V getParent() {
		return parent;
	}

	/**
	 * Just to reduce line size, this method is equivalent to <code>getParent().get()</code>
	 * 
	 * @return The object to modify.
	 */
	protected U get() {
		return getParent().get();
	}

	@Override
	public Map<String, W> getChildren() {
		return Collections.unmodifiableMap(editions);
	}

	@Override
	public List<W> getChildrenByLabelName(String labelName) {
		return descendants.stream().filter(edition -> edition.getLabel().equals(labelName)).collect(Collectors.toList());
	}

	protected void internalSetAvailable(boolean available) {
		if (!modifiable)
			return;
		this.available = available;
		for (W edition : getChildren().values())
			edition.setAvailable(available);
	}

	protected void internalSetModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	protected void internalAdd(W elt) {
		internalAddToDescendants(elt);
		editions.put(elt.getLabel(), elt);
	}

	protected void internalRemove(W elt) {
		internalRemoveFromDescendants(elt);
		editions.remove(elt.getLabel());
	}

	/**
	 * Parses the string argument as a signed decimal integer. The characters in the string must all be decimal digits, except that
	 * the first character may be an ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to indicate a negative value or an ASCII
	 * plus sign {@code '+'} ({@code '\u005Cu002B'}) to indicate a positive value. The resulting integer value is returned, exactly as
	 * if the argument and the radix 10 were given as arguments to the {@link #parseInt(java.lang.String, int)} method.
	 *
	 * @param number a {@code String} containing the {@code int} representation to be parsed
	 * @return True if the given string contains a parsable integer OR is empty, false otherwise.
	 */
	protected boolean isNotStrictInt(String number) {
		return number.equals("") || number.equals("-") || isStrictInt(number);
	}

	/**
	 * Parses the string argument as a signed decimal integer. The characters in the string must all be decimal digits, except that
	 * the first character may be an ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to indicate a negative value or an ASCII
	 * plus sign {@code '+'} ({@code '\u005Cu002B'}) to indicate a positive value. The resulting integer value is returned, exactly as
	 * if the argument and the radix 10 were given as arguments to the {@link #parseInt(java.lang.String, int)} method.
	 *
	 * @param number a {@code String} containing the {@code int} representation to be parsed
	 * @return True if the given string contains a parsable integer false otherwise.
	 */
	protected boolean isStrictInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Parses the string argument as a signed decimal double.
	 *
	 * @param number the string to be parsed.
	 * 
	 * @return True if the given string contains a parsable double OR is empty, false otherwise.
	 * 
	 * @throws NullPointerException  if the string is null.
	 * @throws NumberFormatException if the string does not contain a parsable {@code double}.
	 * 
	 * @see java.lang.Double#valueOf(String)
	 */
	protected boolean isNotStrictDouble(String number) {
		return number.equals("") || number.equals("-") || isStrictDouble(number);
	}

	/**
	 * Parses the string argument as a signed decimal double.
	 *
	 * @param number the string to be parsed.
	 * 
	 * @return True if the given string contains a parsable double false otherwise.
	 * 
	 * @throws NullPointerException  if the string is null.
	 * @throws NumberFormatException if the string does not contain a parsable {@code double}.
	 * 
	 * @see java.lang.Double#valueOf(String)
	 */
	protected boolean isStrictDouble(String number) {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Parses the string argument as a {@link LocalTime}.
	 * <p>
	 * The string must represent a valid time and is parsed using {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
	 *
	 * @param text the text to parse such as "10:15:30", not null.
	 * @return True if the given string contains a parsable time OR is empty, false otherwise.
	 */
	protected boolean isNotStrictTime(String time) {
		return time.equals("") || isStrictTime(time);
	}

	/**
	 * Parses the string argument as a {@link LocalTime}.
	 * <p>
	 * The string must represent a valid time and is parsed using {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
	 *
	 * @param text the text to parse such as "10:15:30", not null.
	 * @return True if the given string contains a parsable time, false otherwise.
	 */
	protected boolean isStrictTime(String time) {
		try {
			LocalTime.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * Parses the string argument as a {@link LocalTime}.
	 * <p>
	 * The text is parsed using the formatter, returning a time.
	 *
	 * @param time      the text to parse, not null.
	 * @param formatter the formatter to use, not null.
	 * @return True if the given string contains a parsable time OR is empty, false otherwise.
	 */
	protected boolean isNotStrictTime(String time, DateTimeFormatter formatter) {
		return time.equals("") || isStrictTime(time, formatter);
	}

	/**
	 * Parses the string argument as a {@link LocalTime}.
	 * <p>
	 * The text is parsed using the formatter, returning a time.
	 *
	 * @param time      the text to parse, not null.
	 * @param formatter the formatter to use, not null.
	 * @return True if the given string contains a parsable time, false otherwise.
	 */
	protected boolean isStrictTime(String time, DateTimeFormatter formatter) {
		try {
			LocalTime.parse(time, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * Parses the string argument as a signed decimal integer. The characters in the string must all be decimal digits, except that
	 * the first character may be an ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to indicate a negative value or an ASCII
	 * plus sign {@code '+'} ({@code '\u005Cu002B'}) to indicate a positive value. The resulting integer value is returned, exactly as
	 * if the argument and the radix 10 were given as arguments to the {@link #parseInt(java.lang.String, int)} method.
	 *
	 * @param s a {@code String} containing the {@code int} representation to be parsed.
	 * 
	 * @return the integer value represented by the argument in decimal.
	 * 
	 * @exception NumberFormatException if the string does not contain a parsable integer.
	 */
	protected int getInt(String number) {
		return Integer.parseInt(number);
	}

	/**
	 * Verify the given string start with the specified beginning ignoring case. For example : <br>
	 * <code>str = "IBeGinLIkeThis";<br>
	 * beginning = "ibEginli";<br></code> The method return true.
	 * 
	 * @param str       The string to check.
	 * @param beginning The beginning used as reference.
	 * @return True if the string begin with the given beginning, false otherwise.
	 */
	protected boolean startWithIgnoreCase(String str, String beginning) {
		return str.length() < beginning.length() ? false : str.substring(0, beginning.length()).equalsIgnoreCase(beginning);
	}

	/**
	 * Concatenate each argument present into the given array like : elt1 + " " + elt2 + " " + elt3 +...
	 * 
	 * @param args The array that contains arguments.
	 * 
	 * @return The concatenation of each argument.
	 * 
	 * @see #concat(String[], CharSequence)
	 */
	protected String concat(String[] args) {
		return concat(args, ", ");
	}

	/**
	 * Concatenate each string in the <code>strings</code> array.
	 * 
	 * @param strings   An array that contains string to concatenate.
	 * @param delimiter the sequence of characters to be used between each element added to the concatenation value.
	 * @return The concatenation of each string.
	 * 
	 * @see StringJoiner
	 */
	protected String concat(String[] strings, CharSequence delimiter) {
		StringJoiner joiner = new StringJoiner(delimiter);
		for (String string : strings)
			joiner.add(string);
		return joiner.toString();
	}

	/**
	 * Concatenate each string in the list <code>strings</code> using the given delimiter.
	 * 
	 * @param strings   The list that contains string to concatenate
	 * @param delimiter the sequence of characters to be used between each element added to the concatenation value.
	 * @return The concatenation of each string.
	 */
	protected String concat(List<String> strings, CharSequence delimiter) {
		return concat(strings.toArray(new String[] {}), delimiter);
	}

	/**
	 * Concatenate each string in the list <code>strings</code> using the delimiter ", ".
	 * 
	 * @param strings   The list that contains string to concatenate
	 * @param delimiter the sequence of characters to be used between each element added to the concatenation value.
	 * @return The concatenation of each string.
	 * 
	 * @see #concat(List, CharSequence)
	 */
	protected String concat(List<String> strings) {
		return concat(strings, ", ");
	}

	private void internalAddToDescendants(W elt) {
		for (W element : elt.getChildren().values())
			internalAddToDescendants(element);
		descendants.add(elt);
	}

	private void internalRemoveFromDescendants(W elt) {
		for (W element : elt.getChildren().values())
			internalRemoveFromDescendants(element);
		descendants.remove(elt);
	}
}
