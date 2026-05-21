package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {
    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    // Pusty koszyk: suma 0 i brak najtanszego/najdrozszego produktu.
    @Test
    public void emptyCartIsHandled() {
        assertEquals(0, cart.totalPrice(), 0.001);
        assertEquals(0, cart.totalDiscountPrice(), 0.001);
        assertNull(cart.findCheapest());
        assertNull(cart.findMostExpensive());
        assertTrue(cart.findNCheapest(2).isEmpty());
    }

    // Null jest ignorowany, a produkt za 0 zl dziala normalnie.
    @Test
    public void nullProductIsIgnoredAndZeroPriceWorks() {
        cart.addProduct(null);
        cart.addProduct(new Product("Z", "Gratis", 0));

        assertEquals(1, cart.getProducts().size());
        assertEquals(0, cart.totalPrice(), 0.001);
        assertEquals("Gratis", cart.findCheapest().getName());
    }

    // Szuka jednego najtanszego i jednego najdrozszego produktu.
    @Test
    public void findsCheapestAndMostExpensiveProducts() {
        cart.addProduct(new Product("A", "Tani", 10));
        cart.addProduct(new Product("B", "Sredni", 50));
        cart.addProduct(new Product("C", "Drogi", 100));

        assertEquals("Tani", cart.findCheapest().getName());
        assertEquals("Drogi", cart.findMostExpensive().getName());
    }

    // Szuka n najtanszych i n najdrozszych produktow.
    @Test
    public void findsNProductsByPrice() {
        cart.addProduct(new Product("A", "A", 300));
        cart.addProduct(new Product("B", "B", 100));
        cart.addProduct(new Product("C", "C", 200));

        assertEquals(List.of("B", "C"), names(cart.findNCheapest(2)));
        assertEquals(List.of("A", "C"), names(cart.findNMostExpensive(2)));
        assertTrue(cart.findNCheapest(0).isEmpty());
    }

    // Sortuje domyslnie i po zmianie comparatora.
    @Test
    public void sortsByDefaultAndByCustomComparator() {
        cart.addProduct(new Product("A", "Banana", 100));
        cart.addProduct(new Product("B", "Apple", 100));
        cart.addProduct(new Product("C", "Cherry", 200));

        assertEquals(List.of("Cherry", "Apple", "Banana"), names(cart.getSortedProducts()));

        cart.setComparator(Comparator.comparing(Product::getName));
        assertEquals(List.of("Apple", "Banana", "Cherry"), names(cart.getSortedProducts()));
    }

    // Liczy zwykla sume cen w koszyku.
    @Test
    public void countsTotalPrice() {
        cart.addProduct(new Product("A", "A", 100));
        cart.addProduct(new Product("B", "B", 200));

        assertEquals(300, cart.totalPrice(), 0.001);
    }

    // Promocja 5% dziala tylko powyzej 300 zl.
    @Test
    public void percentageDiscountWorksOnlyAbove300() {
        cart.addProduct(new Product("A", "A", 200));
        cart.addProduct(new Product("B", "B", 200));
        cart.addPromotion(new PercentageDiscountPromotion());
        cart.applyPromotions();

        assertEquals(380, cart.totalDiscountPrice(), 0.001);

        Cart smallCart = new Cart();
        smallCart.addProduct(new Product("A", "A", 100));
        smallCart.addProduct(new Product("B", "B", 100));
        smallCart.addPromotion(new PercentageDiscountPromotion());
        smallCart.applyPromotions();

        assertEquals(200, smallCart.totalDiscountPrice(), 0.001);
    }

    // Promocja 2+1 zeruje cene najtanszego z 3 produktow.
    @Test
    public void buyTwoGetOneFreeMakesCheapestProductFree() {
        cart.addProduct(new Product("A", "A", 100));
        cart.addProduct(new Product("B", "B", 50));
        cart.addProduct(new Product("C", "C", 200));
        cart.addPromotion(new BuyTwoGetOneFreePromotion());
        cart.applyPromotions();

        assertEquals(300, cart.totalDiscountPrice(), 0.001);
    }

    // Przy koszyku powyzej 200 zl dodaje darmowy kubek.
    @Test
    public void freeMugIsAddedAbove200() {
        cart.addProduct(new Product("A", "A", 250));
        cart.addPromotion(new FreeMugPromotion());
        cart.applyPromotions();

        assertEquals(List.of("A", "Firmowy Kubek"), names(cart.getProducts()));
    }

    // Kupon daje 30% znizki na wybrany produkt.
    @Test
    public void couponDiscountsSelectedProductOnce() {
        cart.addProduct(new Product("X", "Produkt", 100));
        cart.addPromotion(new CouponPromotion("X"));
        cart.applyPromotions();

        assertEquals(70, cart.totalDiscountPrice(), 0.001);
    }

    // Promocje mozna usuwac i nakladac ponownie bez dublowania kubka.
    @Test
    public void promotionsCanBeRemovedAndReappliedWithoutDuplicatingMug() {
        cart.addProduct(new Product("A", "A", 400));
        Promotion discount = new PercentageDiscountPromotion();
        Promotion mug = new FreeMugPromotion();

        cart.addPromotion(discount);
        cart.addPromotion(mug);
        cart.applyPromotions();
        cart.applyPromotions();

        assertEquals(2, cart.getProducts().size());
        assertEquals(380, cart.totalDiscountPrice(), 0.001);

        cart.removePromotion(discount);
        cart.applyPromotions();

        assertEquals(400, cart.totalDiscountPrice(), 0.001);
    }

    // Promocje radza sobie z nullem w liscie i z cala lista null.
    @Test
    public void promotionsHandleNullCollectionsAndNullItems() {
        List<Product> result = new BuyTwoGetOneFreePromotion().apply(Arrays.asList(
                new Product("A", "A", 100),
                null,
                new Product("B", "B", 50),
                new Product("C", "C", 200)
        ));

        assertEquals(3, result.size());
        assertEquals(300, result.stream().mapToDouble(Product::getDiscountPrice).sum(), 0.001);
        assertTrue(new FreeMugPromotion().apply(null).isEmpty());
    }

    // Product jest niemutowalny: rabat tworzy nowy obiekt.
    @Test
    public void productIsImmutable() {
        Product original = new Product("A", "A", 100);
        Product discounted = original.withDiscountPrice(70);

        assertEquals(100, original.getDiscountPrice(), 0.001);
        assertEquals(70, discounted.getDiscountPrice(), 0.001);
        assertNotSame(original, discounted);
    }

    private static List<String> names(List<Product> products) {
        return products.stream().map(Product::getName).toList();
    }
}
