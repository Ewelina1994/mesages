package pl.klobut.messages.exception;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String s) {
        System.out.println(s);
    }
}
