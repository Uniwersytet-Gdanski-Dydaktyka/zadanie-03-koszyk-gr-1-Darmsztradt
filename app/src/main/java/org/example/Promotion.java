package org.example;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface Promotion {
    List<Product> apply(List<Product> products);

    static List<Product> copyWithoutNulls(List<Product> products) {
        List<Product> result = new ArrayList<>();
        if (products == null) {
            return result;
        }
        for (Product product : products) {
            if (product != null) {
                result.add(product);
            }
        }
        return result;
    }

    static double totalDiscountPrice(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            total += product.getDiscountPrice();
        }
        return total;
    }
}
