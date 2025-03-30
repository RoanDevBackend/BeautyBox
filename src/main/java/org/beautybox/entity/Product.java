package org.beautybox.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
        @GeneratedValue(strategy = GenerationType.UUID)
            @Column(length = 36)
    String id;
    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "text")
    String description;
    @ManyToOne
            @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne
            @JoinColumn(name = "brand_id")
    Brand brand;
}
