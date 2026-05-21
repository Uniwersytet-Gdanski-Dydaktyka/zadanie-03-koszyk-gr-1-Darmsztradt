package org.example;

public final class Product {
    private final String code;
    private final String name;
    private final double price;
    private final double discountPrice;

    public Product(String code, String name, double price) {
        this(code, name, price, price);
    }

    public Product(String code, String name, double price, double discountPrice) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public Product withDiscountPrice(double newDiscountPrice) {
        return new Product(code, name, price, newDiscountPrice);
    }

    @Override
    public String toString() {
        if (discountPrice == price) {
            return name + " (" + code + ") " + price + " zl";
        }
        return name + " (" + code + ") " + price + " zl -> " + discountPrice + " zl";
    }
}
