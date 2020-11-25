package com.myself.crypto.pets.utils;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.repositories.specifications.CurrencySpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

@Getter
public class CurrencyFilter {
    private Specification<Currency> spec;
    private StringBuilder filterDefinition;

    public CurrencyFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            float minPrice = Float.parseFloat(map.get("min_price"));
            spec = spec.and(CurrencySpecifications.priceGreaterOrEqualsThan(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            float maxPrice = Float.parseFloat(map.get("max_price"));
            spec = spec.and(CurrencySpecifications.priceLesserOrEqualsThan(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if (map.containsKey("title") && !map.get("title").isEmpty()) {
            String title = map.get("title");
            spec = spec.and(CurrencySpecifications.titleLike(title));
            filterDefinition.append("&title=").append(title);
        }
        if (categories != null && !categories.isEmpty()) {
            Specification specCategories = null;
            for (Category c : categories) {
                if (specCategories == null) {
                    specCategories = CurrencySpecifications.categoryIs(c);
                } else {
                    specCategories = specCategories.or(CurrencySpecifications.categoryIs(c));
                }
            }
            spec = spec.and(specCategories);
        }
    }
}
