package github.caicosantos.library.api.exceptions;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException(String msg) {
        super(msg);
    }
}
