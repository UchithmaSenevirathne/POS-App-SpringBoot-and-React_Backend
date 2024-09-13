package lk.ijse.posreactspringbootbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    private int order_id;
    // Many orders can be placed by one user
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity user;
    // Many-to-Many relationship with ItemEntity
    @ManyToMany
    @JoinTable(
            name = "order_items", // Join table
            joinColumns = @JoinColumn(name = "order_id"), // Column representing orders in the join table
            inverseJoinColumns = @JoinColumn(name = "item_id") // Column representing items in the join table
    )
    private List<ItemEntity> items;
    private int quantity;
    private double total_price;
    private String order_date;
}
