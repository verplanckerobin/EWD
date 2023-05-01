package exceptions;

public class BoekNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BoekNotFoundException(String isbn) {
	super(String.format("Boek met isbn %s niet gevonden", isbn));
    }
}
