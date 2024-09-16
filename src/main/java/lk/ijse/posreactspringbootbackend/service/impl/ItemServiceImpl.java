package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.CategoryDAO;
import lk.ijse.posreactspringbootbackend.dao.CustomItemDAO;
import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.entity.CategoryEntity;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.exception.ItemNotFoundException;
import lk.ijse.posreactspringbootbackend.exception.UserNotFoundException;
import lk.ijse.posreactspringbootbackend.service.ItemService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        // Retrieve the category entity by ID
        CategoryEntity category = categoryDAO.findById(itemDTO.getCategoryId())
                .orElseThrow(() -> new DataPersistFailedException("Category not found"));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemName(itemDTO.getItemName());
        itemEntity.setItemPrice(itemDTO.getItemPrice());
        itemEntity.setItemQuantity(itemDTO.getItemQuantity());
        itemEntity.setItemImage(itemDTO.getItemImage());
        itemEntity.setCategory(category);  // Set the category entity

        // Save the item with the category reference
        itemDAO.save(itemEntity);
    }

    @Override
    public void updateItem(ItemDTO updateBuidItemDto) {
        Optional<ItemEntity> tmpItemEntity = itemDAO.findById(updateBuidItemDto.getItemId());
        if(!tmpItemEntity.isPresent()){
            throw new UserNotFoundException("Item Not Found");
        }else {
            CategoryEntity category = categoryDAO.findById(updateBuidItemDto.getCategoryId())
                    .orElseThrow(() -> new DataPersistFailedException("Category not found"));

            tmpItemEntity.get().setItemName(updateBuidItemDto.getItemName());
            tmpItemEntity.get().setItemPrice(updateBuidItemDto.getItemPrice());
            tmpItemEntity.get().setItemQuantity(updateBuidItemDto.getItemQuantity());
            tmpItemEntity.get().setCategory(category);
            tmpItemEntity.get().setItemImage(updateBuidItemDto.getItemImage());
        }
    }

    @Override
    public ItemDTO getSelectedItem(int itemId) {
        return mapping.convertToItemDTO(itemDAO.getItemEntitiesByItemId(itemId));
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> itemDTOS = mapping.convertToItemDTOList(itemDAO.findAll());
        return itemDTOS;
    }

    @Override
    public void deleteItem(int itemId) {
        Optional<ItemEntity> tmpItemEntity = itemDAO.findById(itemId);
        if(!tmpItemEntity.isPresent()){
            throw new ItemNotFoundException("Item Not Found");
        }else {
            itemDAO.deleteById(itemId);
        }
    }

    @Override
    public List<ItemDTO> getItemsByCategory(int categoryId) {
        List<ItemDTO> itemDTOS = mapping.convertToItemDTOList(itemDAO.getByCategoryId(categoryId));
        return itemDTOS;
    }
}
