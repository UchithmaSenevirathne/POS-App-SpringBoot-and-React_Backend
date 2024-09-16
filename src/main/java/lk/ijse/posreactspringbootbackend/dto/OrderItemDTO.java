package lk.ijse.posreactspringbootbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDTO {
    private int itemId;
    private String itemName;
    private Double itemPrice;
    private String itemImage;
    private int quantity;
}
