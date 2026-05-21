package org.example;

import java.util.List;

public class PercentageDiscountPromotion implements Promotion {
    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> result = Promotion.copyWithoutNulls(products);

        if (Promotion.totalDiscountPrice(result) <= 300) {
            return result;
        }

        return result.stream()
                .map(product -> product.withDiscountPrice(product.getDiscountPrice() * 0.95))
                .toList();
    }
}
