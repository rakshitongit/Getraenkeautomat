package org.arvato.systems.aufgabe.classes;

public enum Getraenk {
    COLA("COLA", 150), PEPSI("Pepsi", 170), SODA("Soda", 160), WASSER("Wasser", 150);
    private String name;
    private int price;

    Getraenk(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
