package fr.pederobien.minecraftdevelopmenttoolkit.exceptions;

public class NotAvailableArgumentException extends EditionException {
	private static final long serialVersionUID = 1L;
	private String argument;

	public NotAvailableArgumentException(String label, String argument) {
		super(label, "The argument \"" + argument + "\" associated to the command \"" + label + "\" is not available.");
		this.argument = argument;
	}

	/**
	 * @return The not available argument
	 */
	public String getArgument() {
		return argument;
	}
}
