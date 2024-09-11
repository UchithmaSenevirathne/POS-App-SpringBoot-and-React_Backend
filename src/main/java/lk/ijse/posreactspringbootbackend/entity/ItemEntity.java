package lk.ijse.posreactspringbootbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue
    private int itemId;
    private String itemName;
    private Double itemPrice;
    private int itemQuantity;
    @Column(columnDefinition = "LONGTEXT")
    private String itemImage;
}
