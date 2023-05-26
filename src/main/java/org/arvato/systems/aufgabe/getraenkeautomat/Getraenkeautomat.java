package org.arvato.systems.aufgabe.getraenkeautomat;

import org.arvato.systems.aufgabe.classes.Container;
import org.arvato.systems.aufgabe.classes.Geld;
import org.arvato.systems.aufgabe.classes.Getraenk;

import java.util.List;

public interface Getraenkeautomat {
    int selectItemAndGetPrice(Getraenk getraenk);
    void insertCoin(int coin);
    Container<Getraenk, List<Geld>> collectItemAndChange();
    void reset();
}
