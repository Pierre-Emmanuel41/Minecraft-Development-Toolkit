package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.BooleanParseException;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.ICommonEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericEdition;

public abstract class AbstractCommonEdition<T, U extends IGenericEdition<T>, V> extends AbstractGenericEdition<T> implements ICommonEdition<T, U, V> {
	private boolean isAvailable, isModifiable;
	private Map<String, U> editions;

	public AbstractCommonEdition(String label, T explanation) {
		super(label, explanation);
		editions = new HashMap<String, U>();
		isAvailable = true;
		isModifiable = true;
	}

	@Override
	public Map<String, U> getChildren() {
		return Collections.unmodifiableMap(editions);
	}

	@Override
	public boolean isAvailable() {
		return isAvailable;
	}

	@Override
	public boolean isModifiable() {
		return isModifiable;
	}

	protected void internalSetAvailable(boolean available) {
		if (!isModifiable)
			return;
		this.isAvailable = available;
	}

	protected void internalSetModifiable(boolean modifiable) {
		this.isModifiable = modifiable;
	}

	protected void internalAdd(U elt) {
		editions.put(elt.getLabel(), elt);
	}

	protected void internalRemove(U elt) {
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
			getInt(number);
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
			getDouble(number);
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
			getTime(time);
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
	 * Returns a new {@code double} initialized to the value represented by the specified {@code String}, as performed by the
	 * {@code valueOf} method of class {@code Double}.
	 *
	 * @param number The string to be parsed.
	 * 
	 * @return The {@code double} value represented by the string argument.
	 * 
	 * @throws NullPointerException  If the string is null.
	 * @throws NumberFormatException If the string does not contain a parsable {@code double}.
	 * @see java.lang.Double#valueOf(String)
	 */
	protected double getDouble(String number) {
		return Double.parseDouble(number);
	}

	/**
	 * Obtains an instance of {@code LocalTime} from a text string such as {@code 10:15}.
	 * <p>
	 * The string must represent a valid time and is parsed using {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
	 *
	 * @param time The time to parse such as "10:15:30", not null.
	 * 
	 * @return The parsed local time, not null.
	 * 
	 * @throws DateTimeParseException If the text cannot be parsed.
	 */
	protected LocalTime getTime(String time) {
		return LocalTime.parse(time);
	}

	/**
	 * Parses the string argument as a boolean. The {@code boolean} returned represents the value {@code true} if and only if the
	 * string argument equals, ignoring case, to the string {@code "true"} or represents the value {@code false} if and only if the
	 * string argument equals, ignoring case, to the string {@code "false"}.
	 * <p>
	 * Example: {@code Boolean.parseBoolean("True")} returns {@code true}.<br>
	 *
	 * @param bool the {@code String} containing the boolean representation to be parsed
	 * @return the boolean represented by the string argument
	 * 
	 * @throws BooleanParseException If the the string argument is neither equal, ignoring case, to {@code "true"} nor
	 *                               {@code "false"}.
	 */
	protected boolean getBoolean(String bool) {
		if (bool.equalsIgnoreCase("true"))
			return true;
		if (bool.equalsIgnoreCase("false"))
			return false;
		throw new BooleanParseException(bool);
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
	 * Concatenate each argument present into the given array like : elt1 + ", " + elt2 + ", " + elt3 +...
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
}
