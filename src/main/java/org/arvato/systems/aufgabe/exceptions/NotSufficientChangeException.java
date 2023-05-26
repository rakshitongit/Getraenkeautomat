package org.arvato.systems.aufgabe.exceptions;

/**
 * Der Automat löst diese Exception aus, um darauf hinzuweisen,
 * dass er nicht über genügend Kleingeld verfügt,
 * um diese Anforderung zu erfüllen.
 */
public class NotSufficientChangeException extends RuntimeException {
    private String message;

    public NotSufficientChangeException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
