package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericEdition;

public abstract class AbstractGenericEdition<T> implements IGenericEdition<T> {
	private String label;
	private T explanation;

	public AbstractGenericEdition(String label, T explanation) {
		this.label = label;
		this.explanation = explanation;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public T getExplanation() {
		return explanation;
	}

	@Override
	public int compareTo(IGenericEdition<T> o) {
		return label.compareTo(o.getLabel());
	}

	/**
	 * @return An empty array list.
	 */
	protected List<String> emptyList() {
		return new ArrayList<String>();
	}

	/**
	 * Filter each string from the given stream using condition : <code>str.contains(filter)</code>
	 * 
	 * @param stream A stream that contains string to filter.
	 * @param filter The condition used to filter the previous stream.
	 * 
	 * @return A list of string from the given stream that contains the filter.
	 */
	protected List<String> filter(Stream<String> stream, String filter) {
		return stream.filter(str -> str.contains(filter)).collect(Collectors.toList());
	}

	/**
	 * Copies the specified range of the specified array into a new array. The initial index of the range (<tt>from</tt>) must lie
	 * between zero and <tt>original.length</tt>, inclusive. The value at <tt>original[from]</tt> is placed into the initial element
	 * of the copy (unless <tt>from == original.length</tt> or <tt>from == to</tt>). Values from subsequent elements in the original
	 * array are placed into subsequent elements in the copy. The final index of the range (<tt>to</tt>), which must be greater than
	 * or equal to <tt>from</tt>, may be greater than <tt>original.length</tt>, in which case <tt>null</tt> is placed in all elements
	 * of the copy whose index is greater than or equal to <tt>original.length - from</tt>. The length of the returned array will be
	 * <tt>to - from</tt>.
	 * <p>
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array from which a range is to be copied.
	 * @param from     the initial index of the range to be copied, inclusive.
	 * @param to       the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with nulls to obtain the
	 *         required length
	 * @throws ArrayIndexOutOfBoundsException if {@code from < 0} or {@code from > original.length}
	 * @throws IllegalArgumentException       if <tt>from &gt; to</tt>
	 * @throws NullPointerException           if <tt>original</tt> is null
	 */
	protected String[] extract(String[] original, int from, int to) {
		return Arrays.copyOfRange(original, from, to);
	}

	/**
	 * Copy the specified array into a new array. This method is equivalent to call {@link #extract(String[], int, int)} with
	 * parameter "to" equals args.length.
	 * 
	 * @param original the array from which a range is to be copied.
	 * @param from     the initial index of the range to be copied, inclusive.
	 * 
	 * @return a new array containing the specified range from the original array, truncated or padded with nulls to obtain the
	 *         required length
	 */
	protected String[] extract(String[] original, int from) {
		return extract(original, from, original.length);
	}
}
