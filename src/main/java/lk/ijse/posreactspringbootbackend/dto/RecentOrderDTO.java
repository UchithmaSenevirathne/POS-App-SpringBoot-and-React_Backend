package lk.ijse.posreactspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentOrderDTO {
    private int orderId;
    private int userId;
    private String orderDate;
    private double orderTotal;
    private String userAddress;
}
