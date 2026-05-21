package org.example;

import java.util.List;

public class BuyTwoGetOneFreePromotion implements Promotion {
    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> result = Promotion.copyWithoutNulls(products);
        if (result.size() < 3) {
            return result;
        }

        int cheapestIndex = 0;
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i).getDiscountPrice() < result.get(cheapestIndex).getDiscountPrice()) {
                cheapestIndex = i;
            }
        }

        Product cheapest = result.get(cheapestIndex);
        result.set(cheapestIndex, cheapest.withDiscountPrice(0));
        return result;
    }
}
