package org.example;

import java.util.List;

public class CouponPromotion implements Promotion {
    private final String productCode;

    public CouponPromotion(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> result = Promotion.copyWithoutNulls(products);
        if (productCode == null) {
            return result;
        }

        for (int i = 0; i < result.size(); i++) {
            Product product = result.get(i);
            if (productCode.equals(product.getCode())) {
                result.set(i, product.withDiscountPrice(product.getDiscountPrice() * 0.70));
                break;
            }
        }
        return result;
    }
}
