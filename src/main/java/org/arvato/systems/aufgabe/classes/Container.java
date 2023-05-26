package org.arvato.systems.aufgabe.classes;

/**
 * Ein parametrisierte Utility-Class, das zwei verschiedene Objekte aufnehmen kann.
 * @param <G> z.B Getraenke
 * @param <M> z.B WechselGeld
 */
public class Container<G, M> {
    private G first;
    private M second;

    public Container(G first, M second){
        this.first = first;
        this.second = second;
    }

    public G getFirst(){
        return first;
    }

    public M getSecond(){
        return second;
    }

    @Override
    public String toString() {
        return "Container{" +
                "Getraenk=" + first +
                ", WechselGeld=" + second +
                '}';
    }
}
