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
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue
    private int cat_id;
    private String cat_name;
    // One cat can have many items
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ItemEntity> items;
}
