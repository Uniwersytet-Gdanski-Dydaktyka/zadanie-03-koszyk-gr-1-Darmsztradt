package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Cart {
    private final List<Product> originalProducts = new ArrayList<>();
    private final List<Promotion> promotions = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Comparator<Product> comparator = defaultComparator();

    public static Comparator<Product> defaultComparator() {
        return Comparator.comparingDouble(Product::getPrice)
                .reversed()
                .thenComparing(Product::getName);
    }

    public void addProduct(Product product) {
        if (product != null) {
            originalProducts.add(product);
            products.add(product);
        }
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void setComparator(Comparator<Product> comparator) {
        if (comparator != null) {
            this.comparator = comparator;
        }
    }

    public List<Product> getSortedProducts() {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort(comparator);
        return sorted;
    }

    public Product findCheapest() {
        return products.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public Product findMostExpensive() {
        return products.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public List<Product> findNCheapest(int n) {
        if (n <= 0) {
            return List.of();
        }
        return products.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .limit(n)
                .toList();
    }

    public List<Product> findNMostExpensive(int n) {
        if (n <= 0) {
            return List.of();
        }
        return products.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(n)
                .toList();
    }

    public double totalPrice() {
        return products.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public double totalDiscountPrice() {
        return products.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Product::getDiscountPrice)
                .sum();
    }

    public void addPromotion(Promotion promotion) {
        if (promotion != null) {
            promotions.add(promotion);
        }
    }

    public void removePromotion(Promotion promotion) {
        promotions.remove(promotion);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

    public void applyPromotions() {
        List<Product> current = new ArrayList<>(originalProducts);
        for (Promotion promotion : promotions) {
            List<Product> changed = promotion.apply(current);
            if (changed != null) {
                current = new ArrayList<>(changed);
            }
        }
        products = current;
    }
}
