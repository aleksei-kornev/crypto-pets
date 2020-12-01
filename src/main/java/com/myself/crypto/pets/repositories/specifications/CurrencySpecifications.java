package com.myself.crypto.pets.repositories.specifications;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecifications {
    public static Specification<Currency> priceGreaterOrEqualsThan(float minPrice) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("USD"), minPrice);
    }

    public static Specification<Currency> priceLesserOrEqualsThan(float maxPrice) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("USD"), maxPrice);
    }

    public static Specification<Currency> tickerLike(String ticker) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("ticker"), String.format("%%%s%%", ticker));
    }

    public static Specification<Currency> categoryIs(Category category) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> {
            
//            Join join = root.join("categories");
//            return criteriaBuilder.equal(join.get("id"), category.getId());
             return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
