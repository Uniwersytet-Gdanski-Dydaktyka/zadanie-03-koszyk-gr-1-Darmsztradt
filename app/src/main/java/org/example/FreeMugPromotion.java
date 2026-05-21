package org.example;

import java.util.List;

public class FreeMugPromotion implements Promotion {
    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> result = Promotion.copyWithoutNulls(products);
        if (Promotion.totalDiscountPrice(result) > 200) {
            result.add(new Product("MUG", "Firmowy Kubek", 0));
        }
        return result;
    }
}
