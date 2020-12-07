package com.myself.crypto.pets.repositories.specifications;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import org.springframework.data.jpa.domain.Specification;

import javax.sound.sampled.Port;

public class PortfolioSpecifications {
    public static Specification<Portfolio> costGreaterOrEqualsThan(float minCost) {
        return (Specification<Portfolio>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    public static Specification<Portfolio> costLesserOrEqualsThan(float maxCost) {
        return (Specification<Portfolio>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
    }
}
