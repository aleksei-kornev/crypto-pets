package com.myself.crypto.pets.repositories.specifications;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecifications {
    public static Specification<Currency> priceGreaterOrEqualsThan(int minPrice) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Currency> priceLesserOrEqualsThan(int maxPrice) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Currency> titleLike(String title) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Currency> categoryIs(Category category) {
        return (Specification<Currency>) (root, criteriaQuery, criteriaBuilder) -> {
            
//            Join join = root.join("categories");
//            return criteriaBuilder.equal(join.get("id"), category.getId());
             return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
