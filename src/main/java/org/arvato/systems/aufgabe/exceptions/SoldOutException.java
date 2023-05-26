package org.arvato.systems.aufgabe.exceptions;

/**
 * Der Automat löst diese Exception aus,
 * wenn der Benutzer ein Produkt anfordert,
 * das ausverkauft ist.
 */
public class SoldOutException extends RuntimeException {
    private String message;

    public SoldOutException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }
}