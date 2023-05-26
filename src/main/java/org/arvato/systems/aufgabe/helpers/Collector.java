package org.arvato.systems.aufgabe.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein parametrisierte Utility-Class, das neue Objecte hinzufuegt.
 * @param <T>
 */
public class Collector<T> {
    private Map<T, Integer> collector = new HashMap<T, Integer>();

    public int getQuantity(T item){
        return this.collector.getOrDefault(item, 0);
    }

    public void add(T item){
        this.collector.put(item, this.collector.getOrDefault(item, 0) + 1);
    }

    public void remove(T item) {
        if (hasItem(item)) {
            this.collector.put(item, this.collector.get(item) - 1);
        }
    }

    public boolean hasItem(T item){
        return getQuantity(item) > 0;
    }

    public void clear() {
        this.collector.clear();
    }

    public void put(T item, int quantity) {
        this.collector.put(item, quantity);
    }

    @Override
    public String toString() {
        return "Getraenke = Quantity: " + collector;
    }
}
