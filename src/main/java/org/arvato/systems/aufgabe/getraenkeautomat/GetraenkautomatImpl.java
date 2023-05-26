package org.arvato.systems.aufgabe.getraenkeautomat;

import org.arvato.systems.aufgabe.classes.Container;
import org.arvato.systems.aufgabe.classes.Geld;
import org.arvato.systems.aufgabe.classes.Getraenk;
import org.arvato.systems.aufgabe.exceptions.NotFullPaidException;
import org.arvato.systems.aufgabe.exceptions.NotSufficientChangeException;
import org.arvato.systems.aufgabe.exceptions.SoldOutException;
import org.arvato.systems.aufgabe.helpers.Collector;

import java.util.*;

public class GetraenkautomatImpl implements Getraenkeautomat {
    private Collector<Geld> cashCollector = new Collector<Geld>();
    private Collector<Getraenk> itemCollector = new Collector<Getraenk>();
    private Getraenk currentItem;

    public Collector<Geld> getCashCollector() {
        return cashCollector;
    }

    public Collector<Getraenk> getItemCollector() {
        return itemCollector;
    }

    public Getraenk getCurrentItem() {
        return currentItem;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    private long currentBalance;

    public GetraenkautomatImpl() {
        initialize(cashCollector, itemCollector, Optional.empty());
    }

    /**
     * initialize machine with 5 (if not specified) coins of each denomination
     * and 5 (if not specified) items of drinks
     * @param Geld collector
     * @param Getraenk collector
     */
    private static void initialize(Collector<Geld> c1, Collector<Getraenk> c2, Optional<Integer> quantity){

        for(Geld c : Geld.values()) {
            c1.put(c, quantity.orElse(5));
        }

        for(Getraenk i : Getraenk.values()) {
            c2.put(i, quantity.orElse(5));
        }
    }

    public void displaySelectedItem() {
        if(currentItem != null) {
            System.out.println("Selected Getraenke: " + currentItem.getName());
        } else {
            throw new RuntimeException("Please first select a Getraenke using option 3");
        }
    }

    @Override
    public int selectItemAndGetPrice(Getraenk getraenk) {
        if(getraenk == null) {
            throw new RuntimeException("Wählen Sie die richtige Getränk!");
        }
        if(itemCollector.hasItem(getraenk)) {
            this.currentItem = getraenk;
            return getraenk.getPrice();
        }
        throw new SoldOutException("Getränk Ausverkauft, Bitte kaufen Sie einen anderen Getränk");
    }

    @Override
    public void insertCoin(int val) throws RuntimeException {
        Optional<Geld> geld = Optional.empty();
        for(Geld g: Geld.values()) {
            if(Objects.equals(g.getValue(), val)) {
                geld = Optional.of(Geld.valueOf(g.name()));
            }
        }
        if(geld.isPresent()) {
            currentBalance += geld.get().getValue();
            cashCollector.add(geld.get());
        } else {
            throw new RuntimeException("Invalid Currency!");
        }
    }

    private void updateCashCollector(List<Geld> change) {
        for(Geld c : change){
            cashCollector.remove(c);
        }
    }

    @Override
    public Container<Getraenk, List<Geld>> collectItemAndChange() {
        Getraenk getraenk = collectGetraenk();
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Geld> wechselgeld = getWechselGeld(changeAmount);
        return new Container<>(getraenk, wechselgeld);
    }

    private Getraenk collectGetraenk() {
        if(isFullPaid()){
            if(hasSufficientChange()){
                itemCollector.remove(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("Nicht ausreichende Wechselgeld des Automates");

        }
        throw new NotFullPaidException("Price not full paid, remaining : ",
                currentItem.getPrice() - currentBalance);
    }

    private boolean isFullPaid() {
        return currentBalance >= currentItem.getPrice();
    }

    private boolean hasSufficientChange() {
        try {
            this.getWechselGeld(currentBalance - currentItem.getPrice());
        } catch (NotSufficientChangeException ex) {
            return false;
        }
        return true;
    }

    private List<Geld> getWechselGeld(long amount) throws NotSufficientChangeException, NotFullPaidException {
        List<Geld> coins = Collections.emptyList();

        if(amount > 0){
            coins = new ArrayList<>();
            long balance = amount;
            while (balance > 0) {
                if(balance >= Geld.EURO_2.getValue()
                        && cashCollector.hasItem(Geld.EURO_2)){
                    coins.add(Geld.EURO_2);
                    balance = balance - Geld.EURO_2.getValue();
                }else if(balance >= Geld.EURO.getValue()
                        && cashCollector.hasItem(Geld.EURO)){
                    coins.add(Geld.EURO);
                    balance = balance - Geld.EURO.getValue();
                }else if(balance >= Geld.CENTS_50.getValue()
                        && cashCollector.hasItem(Geld.CENTS_50)){
                    coins.add(Geld.CENTS_50);
                    balance = balance - Geld.CENTS_50.getValue();
                }else if(balance >= Geld.CENTS_20.getValue()
                        && cashCollector.hasItem(Geld.CENTS_20)){
                    coins.add(Geld.CENTS_20);
                    balance = balance - Geld.CENTS_20.getValue();
                }else if(balance >= Geld.CENTS_10.getValue()
                        && cashCollector.hasItem(Geld.CENTS_10)){
                    coins.add(Geld.CENTS_10);
                    balance = balance - Geld.CENTS_10.getValue();
                }else {
                    throw new NotSufficientChangeException("Nicht ausreichend Wechselgeld, bitte versuchen Sie ein anderes Produkt");
                }
            }
        }

        return Collections.unmodifiableList(coins);
    }

    @Override
    public void reset() {
        cashCollector.clear();
        itemCollector.clear();
        currentItem = null;
        currentBalance = 0;
        initialize(cashCollector, itemCollector, Optional.empty());
    }

    public void printStats() {
        System.out.println("Aktualle Getraenke im Automat : " + itemCollector);
        System.out.println("Aktualle Geld im Automat : " + cashCollector);
    }

    public void printBalance() {
        System.out.println("Balance:" + currentBalance);
    }

    public void displayGetraenkeList() {
        int i = 0;
        for (Getraenk g: Getraenk.values()) {
            System.out.println(++i + ". Getraenk: " + g.getName() + ", Value: " + g.getPrice());
        }
    }
}
