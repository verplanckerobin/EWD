package exceptions;

public class AuteurNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuteurNotFoundException(String auteurNaam) {
	super(String.format("Auteur met naam %s niet gevonden", auteurNaam));
    }
}
