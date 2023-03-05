package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name= "store")
public class Store extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "storeSequence", sequenceName = "store_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "storeSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", nullable = false)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "count")
    public Integer count;

    @Column(name = "price")
    public Double price;

    @Column(name = "type")
    public String type;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at")
    public String createdAt;

    @Column(name = "updated_at")
    public String updatedAt;
}
