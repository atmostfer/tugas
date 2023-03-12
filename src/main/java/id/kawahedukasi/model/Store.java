package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}
