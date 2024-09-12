package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
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
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        ItemEntity savedItem = itemDAO.save(mapping.convertToItemEntity(itemDTO));

        if (savedItem == null && savedItem.getItemId() == 0) {
            throw new DataPersistFailedException("Item not saved");
        }
    }

    @Override
    public void updateItem(ItemDTO updateBuidItemDto) {
        Optional<ItemEntity> tmpItemEntity = itemDAO.findById(updateBuidItemDto.getItemId());
        if(!tmpItemEntity.isPresent()){
            throw new UserNotFoundException("Item Not Found");
        }else {
            tmpItemEntity.get().setItemName(updateBuidItemDto.getItemName());
            tmpItemEntity.get().setItemPrice(updateBuidItemDto.getItemPrice());
            tmpItemEntity.get().setItemQuantity(updateBuidItemDto.getItemQuantity());
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
        System.out.println(itemDTOS.get(0).getItemId());
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
}
