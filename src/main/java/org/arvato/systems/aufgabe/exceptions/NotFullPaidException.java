package org.arvato.systems.aufgabe.exceptions;

/**
Eine Exception, die vom Automaten ausgelöst wird, wenn ein Benutzer versucht,
einen Artikel abzuholen, ohne den vollen Betrag zu bezahlen.
 **/
public class NotFullPaidException extends RuntimeException {
    private String message;
    private long remaining;

    public NotFullPaidException(String message, long remaining) {
        this.message = message;
        this.remaining = remaining;
    }

    public long getRemaining(){
        return remaining;
    }

    @Override
    public String getMessage() {
        return message + remaining;
    }
}
