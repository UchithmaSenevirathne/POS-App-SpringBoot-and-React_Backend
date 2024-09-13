package lk.ijse.posreactspringbootbackend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private int itemId;
    private String itemName;
    private Double itemPrice;
    private int itemQuantity;
    private String itemImage;
    private int categoryId;
}
