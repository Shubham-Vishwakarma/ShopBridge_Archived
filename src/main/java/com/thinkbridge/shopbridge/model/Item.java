package com.thinkbridge.shopbridge.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "item")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence_generator")
    @SequenceGenerator(name = "item_sequence_generator", sequenceName = "item_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description = "";

    @Column(name = "category", nullable = true)
    private String category = "";

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer default 1")
    private int quantity = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return price == item.price && quantity == item.quantity && Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description) && Objects.equals(category, item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, price, quantity);
    }
}