package com.thinkbridge.shopbridge.filters;

import com.thinkbridge.shopbridge.model.Item;
import com.thinkbridge.shopbridge.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ItemSpecification implements Specification<Item> {

    private final SearchCriteria criteria;

    public ItemSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if(criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(root.get(criteria.getColumn()), criteria.getValue());
        }
        else if(criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.get(criteria.getColumn()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if(root.get(criteria.getColumn()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.get(criteria.getColumn()),  "%" + criteria.getValue() + "%");
            }
            else {
                return criteriaBuilder.equal(root.get(criteria.getColumn()), criteria.getColumn());
            }
        }
        return null;
    }

}
