package lk.ijse.posreactspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOrderDetailsDTO {
    private int orderId;
    private int userId;
    private String orderAddress;
    private int productId;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private String productImage;
    private double orderTotalPrice;
    private String orderDate;
}
