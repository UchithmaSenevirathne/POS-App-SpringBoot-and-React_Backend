package lk.ijse.posreactspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private int order_id;
    private int user_id;
    private int product_id;
    private int quantity;
    private double total_price;
    private String order_date;
}
