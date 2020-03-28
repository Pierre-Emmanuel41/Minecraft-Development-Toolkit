package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import java.util.ArrayList;
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
}
