package org.arvato.systems.aufgabe.classes;

public enum Geld {
    CENTS_10(10), CENTS_20(20), CENTS_50(50), EURO(100), EURO_2(200);
    private int value;

    Geld(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
