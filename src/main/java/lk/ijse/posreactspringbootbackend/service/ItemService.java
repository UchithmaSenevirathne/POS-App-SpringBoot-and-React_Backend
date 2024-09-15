package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO updateBuidItemDto);

    ItemDTO getSelectedItem(int itemId);

    List<ItemDTO> getAllItems();

    void deleteItem(int itemId);

    List<ItemDTO> getItemsByCategory(int categoryId);
}
