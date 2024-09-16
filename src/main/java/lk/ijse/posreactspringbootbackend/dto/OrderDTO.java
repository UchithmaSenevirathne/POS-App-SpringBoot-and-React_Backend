package lk.ijse.posreactspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private int order_id;
    private int user_id;
    private Map<Integer, Integer> itemQuantities;
    private double total_price;
    private String order_date;
}
