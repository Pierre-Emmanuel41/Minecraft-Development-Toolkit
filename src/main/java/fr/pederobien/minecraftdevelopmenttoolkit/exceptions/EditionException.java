package fr.pederobien.minecraftdevelopmenttoolkit.exceptions;

public class EditionException extends MinecraftToolkitException {
	private static final long serialVersionUID = 1L;
	private String label;

	public EditionException(String label, String message) {
		super(message);
		this.label = label;
	}

	/**
	 * @return The label of the not available edition.
	 */
	public String getLabel() {
		return label;
	}
}
