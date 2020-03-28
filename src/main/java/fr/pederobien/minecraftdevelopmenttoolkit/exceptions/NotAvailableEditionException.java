package fr.pederobien.minecraftdevelopmenttoolkit.exceptions;

public class NotAvailableEditionException extends EditionException {
	private static final long serialVersionUID = 1L;

	public NotAvailableEditionException(String label) {
		super(label, "The edition associated to the label " + label + " is not available");
	}
}
