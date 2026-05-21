package org.example;

public class App {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.addProduct(new Product("P1", "Laptop", 2500));
        cart.addProduct(new Product("P2", "Mysz", 50));
        cart.addProduct(new Product("P3", "Klawiatura", 150));

        cart.addPromotion(new PercentageDiscountPromotion());
        cart.addPromotion(new BuyTwoGetOneFreePromotion());
        cart.addPromotion(new FreeMugPromotion());
        cart.addPromotion(new CouponPromotion("P2"));
        cart.applyPromotions();

        for (Product product : cart.getSortedProducts()) {
            System.out.println(product);
        }
        System.out.println("Suma po promocjach: " + cart.totalDiscountPrice() + " zl");
    }
}
