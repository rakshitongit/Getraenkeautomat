import org.arvato.systems.aufgabe.classes.Container;
import org.arvato.systems.aufgabe.classes.Geld;
import org.arvato.systems.aufgabe.classes.Getraenk;
import org.arvato.systems.aufgabe.exceptions.SoldOutException;
import org.arvato.systems.aufgabe.getraenkeautomat.GetraenkautomatImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetraenkeautomatTest {

    GetraenkautomatImpl getraenkautomat = new GetraenkautomatImpl();
    @Test
    public void testGetraenkAutomatIsCorrectlyInitialised() {
        Assert.assertEquals(getraenkautomat.getCurrentBalance(),0);
        Assert.assertNull(getraenkautomat.getCurrentItem());
        Assert.assertNotNull(getraenkautomat.getCashCollector());
        Assert.assertNotNull(getraenkautomat.getItemCollector());
    }

    @Test
    public void testGetraenkAutomatItemSelectedCorrectly() {
        getraenkautomat.selectItemAndGetPrice(Getraenk.PEPSI);
        Assert.assertEquals(getraenkautomat.getCurrentBalance(), 0);
        Assert.assertNotNull(getraenkautomat.getCurrentItem());
        Assert.assertEquals(getraenkautomat.getCurrentItem(), Getraenk.PEPSI);
    }

    @Test
    public void testGetraenkAutomatItemSelectedInCorrectly() {
        Assert.assertThrows(RuntimeException.class, ()-> getraenkautomat.selectItemAndGetPrice(null));
        Assert.assertEquals(getraenkautomat.getCurrentBalance(), 0);
        Assert.assertNull(getraenkautomat.getCurrentItem());
        Assert.assertNull(getraenkautomat.getCurrentItem());
    }

    @Test
    public void testGetraenkAutomatCoinsInserted() {
        // Correct Coins
        getraenkautomat.insertCoin(100);
        Assert.assertEquals(getraenkautomat.getCurrentBalance(), 100);
        // Incorrect Coins
        Assert.assertThrows(RuntimeException.class, ()-> getraenkautomat.insertCoin(250));
        Assert.assertThrows(RuntimeException.class, ()-> getraenkautomat.insertCoin(25));
        Assert.assertThrows(RuntimeException.class, ()-> getraenkautomat.insertCoin(5));
        Assert.assertEquals(getraenkautomat.getCurrentBalance(), 100);
    }

    @Test
    public void testGetraenkAutomatSelectAndCheckout() {
        // First checkout (not possible throw exception)
        Assert.assertThrows(RuntimeException.class, ()-> getraenkautomat.collectItemAndChange());
        // Correct Coins
        getraenkautomat.insertCoin(200);
        Assert.assertEquals(getraenkautomat.getCurrentBalance(), 200);
        // Select drink
        getraenkautomat.selectItemAndGetPrice(Getraenk.PEPSI);
        // Checkout
        Container<Getraenk, List<Geld>> c = getraenkautomat.collectItemAndChange();
        Assert.assertEquals(c.getFirst(), Getraenk.PEPSI);
        Assert.assertEquals(c.getSecond(), List.of(Geld.CENTS_20, Geld.CENTS_10));
    }

    @Test
    public void testGetraenkAutomatSelectAndCheckout6Times() {
        getraenkautomat.reset();
        for (int i = 0; i < 5; i++) {
            // Correct Coins
            getraenkautomat.insertCoin(200);
            // Select drink
            getraenkautomat.selectItemAndGetPrice(Getraenk.PEPSI);
            // Checkout
            Container<Getraenk, List<Geld>> c = getraenkautomat.collectItemAndChange();
            Assert.assertEquals(c.getFirst(), Getraenk.PEPSI);
        }
        // Correct Coins
        getraenkautomat.insertCoin(200);
        // Select drink (with exception)
        Assert.assertThrows(SoldOutException.class, ()-> getraenkautomat.selectItemAndGetPrice(Getraenk.PEPSI));
    }

}
