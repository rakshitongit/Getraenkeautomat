package org.arvato.systems.aufgabe;

import org.arvato.systems.aufgabe.classes.Getraenk;
import org.arvato.systems.aufgabe.getraenkeautomat.GetraenkautomatImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GetraenkautomatImpl getraenkautomat = new GetraenkautomatImpl();
        Scanner sc = new Scanner(System.in);
        int display = displayText(sc);
        while (display != 0) {
            switch (display) {
                case 1 -> getraenkautomat.reset();
                case 2 -> getraenkautomat.printStats();
                case 3 -> {
                    getraenkautomat.displayGetraenkeList();
                    System.out.println("Select the Getraenk: ");
                    int g = sc.nextInt();
                    int price = getraenkautomat.selectItemAndGetPrice(Getraenk.values()[g - 1]);
                    System.out.println("Price of " + Getraenk.values()[g - 1].getName() + " = " + price);
                }
                case 4 -> {
                    System.out.println("Enter money (in the format e.g. 100 for 1 eur, 200 for 2 eur, 50 for 50 cents: ");
                    getraenkautomat.insertCoin(sc.nextInt());
                }
                case 5 -> {
                    getraenkautomat.displaySelectedItem();
                    System.out.println("Are you sure? (0/1)");
                    int confirmation = sc.nextInt();
                    if (confirmation == 1) {
                        System.out.println(getraenkautomat.collectItemAndChange());
                    }
                }
                case 6 -> getraenkautomat.printBalance();
                default -> display = 0;
            }
            display = displayText(sc);
        }
    }

    private static int displayText(Scanner sc) {
        System.out.println("********************************** Menu **********************************");
        System.out.println("Option 1: Refill Automat");
        System.out.println("Option 2: Display Statistics");
        System.out.println("Option 3: Select and check price of Getraenke");
        System.out.println("Option 4: Enter Money");
        System.out.println("Option 5: Collect the Getraenke");
        System.out.println("Option 6: Check Balance");
        System.out.println("Option 0: Exit");
        System.out.print("Wählen Sie ein Option aus: ");
        int display = sc.nextInt();
        System.out.println("********************************** End ***********************************");
        return display;
    }
}