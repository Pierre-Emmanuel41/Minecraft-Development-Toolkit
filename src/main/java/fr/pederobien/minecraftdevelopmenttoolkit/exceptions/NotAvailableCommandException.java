package fr.pederobien.minecraftdevelopmenttoolkit.exceptions;

public class NotAvailableCommandException extends EditionException {
	private static final long serialVersionUID = 1L;

	public NotAvailableCommandException(String label) {
		super(label, "The command " + label + " is not available (yet ?)");
	}
}
