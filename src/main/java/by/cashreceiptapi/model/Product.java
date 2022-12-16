package by.cashreceiptapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
     */
    private Long id;
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name="price", columnDefinition="Decimal(10,2) default '0.00'")
    private Double price;

    @Transient
    private Double oldPrice = null;
    @Transient
    private Double total = null;

    @Column(name = "promo", columnDefinition = "boolean default false")
    private Boolean promo;
}