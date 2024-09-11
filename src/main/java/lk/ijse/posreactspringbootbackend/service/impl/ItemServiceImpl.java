package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.ItemDAO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.service.ItemService;
import lk.ijse.posreactspringbootbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
