package lk.ijse.posreactspringbootbackend.util;

import lk.ijse.posreactspringbootbackend.dto.CategoryDTO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.OrderDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.entity.CategoryEntity;
import lk.ijse.posreactspringbootbackend.entity.ItemEntity;
import lk.ijse.posreactspringbootbackend.entity.OrderEntity;
import lk.ijse.posreactspringbootbackend.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    //user matters mapping
    public UserDTO convertToUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public List<UserDTO> convertToUserDTOList(List<UserEntity> userEntityList) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {}.getType());
    }

    //item matters mapping
    public ItemDTO convertToItemDTO(ItemEntity itemEntity) {
        return modelMapper.map(itemEntity, ItemDTO.class);
    }

    public ItemEntity convertToItemEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, ItemEntity.class);
    }

    public List<ItemDTO> convertToItemDTOList(List<ItemEntity> itemEntityList) {
//        return modelMapper.map(itemEntityList, new TypeToken<List<ItemDTO>>() {}.getType());
        // Customize mapping for categoryId from CategoryEntity
        modelMapper.typeMap(ItemEntity.class, ItemDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getCategory().getCat_id(), ItemDTO::setCategoryId);
        });

        return itemEntityList.stream()
                .map(itemEntity -> modelMapper.map(itemEntity, ItemDTO.class))
                .collect(Collectors.toList());
    }

    public List<ItemDTO> convertToItemDTOList(Optional<ItemEntity> itemEntityOptional) {
        modelMapper.typeMap(ItemEntity.class, ItemDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getCategory().getCat_id(), ItemDTO::setCategoryId);
        });

        return itemEntityOptional.stream()
                .map(itemEntity -> modelMapper.map(itemEntity, ItemDTO.class))
                .collect(Collectors.toList());
    }

    //order matters mapping
    public OrderDTO convertToOrderDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public OrderEntity convertToOrderEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, OrderEntity.class);
    }

    public List<OrderDTO> convertToOrderDTOList(List<OrderEntity> orderEntityList) {
        return modelMapper.map(orderEntityList, new TypeToken<List<OrderDTO>>() {}.getType());
    }


    //category matters mapping
    public CategoryDTO convertToCategoryDTO(CategoryEntity orderEntity) {
        return modelMapper.map(orderEntity, CategoryDTO.class);
    }

    public CategoryEntity convertToCategoryEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }

    public List<CategoryDTO> convertToCategoryDTOList(List<CategoryEntity> orderEntityList) {
        return modelMapper.map(orderEntityList, new TypeToken<List<CategoryDTO>>() {}.getType());
    }

}
