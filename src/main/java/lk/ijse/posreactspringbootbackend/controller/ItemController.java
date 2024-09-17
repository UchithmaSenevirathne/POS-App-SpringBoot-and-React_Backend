package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.exception.ItemNotFoundException;
import lk.ijse.posreactspringbootbackend.exception.UserNotFoundException;
import lk.ijse.posreactspringbootbackend.service.ItemService;
import lk.ijse.posreactspringbootbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/backend/item")
@CrossOrigin
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveItem(
            @RequestPart("itemName") String itemName,
            @RequestPart("unitPrice") String unitPrice,
            @RequestPart("itemQty") String itemQty,
            @RequestPart("categoryId") String categoryId,
            @RequestPart("itemImage") String itemImage
    ) {
        try {
//            String base64Image = Base64.getEncoder().encodeToString(itemImage.getBytes());

            double parsedUnitPrice = Double.parseDouble(unitPrice);
            int parsedItemQty = Integer.parseInt(itemQty);
            int parsedCategoryId = Integer.parseInt(categoryId);

            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemName(itemName);
            itemDTO.setItemPrice(parsedUnitPrice);
            itemDTO.setItemQuantity(parsedItemQty);
            itemDTO.setItemImage(itemImage);
            itemDTO.setCategoryId(parsedCategoryId);

            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateItem
            (@PathVariable ("id") int id,
             @RequestPart("itemName") String updateItemName,
             @RequestPart("unitPrice")String updateUnitPrice,
             @RequestPart("itemQty") String updateItemQty,
             @RequestPart("categoryId") String updateCategoryId,
             @RequestPart("itemImage")String updateItemImage){

        try {
            double parsedUpdatedUnitPrice = Double.parseDouble(updateUnitPrice);
            int parsedUpdatedItemQty = Integer.parseInt(updateItemQty);
            int parsedUpdateCategoryId = Integer.parseInt(updateCategoryId);

            var updateBuidItemDto = new ItemDTO();
            updateBuidItemDto.setItemId(id);
            updateBuidItemDto.setItemName(updateItemName);
            updateBuidItemDto.setItemPrice(parsedUpdatedUnitPrice);
            updateBuidItemDto.setItemQuantity(parsedUpdatedItemQty);
            updateBuidItemDto.setItemImage(updateItemImage);
            updateBuidItemDto.setCategoryId(parsedUpdateCategoryId);

            itemService.updateItem(updateBuidItemDto);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDTO getSelectedItem(@PathVariable ("id") int itemId){
        ItemDTO selectedItem = itemService.getSelectedItem(itemId);
        return selectedItem;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(){
        List<ItemDTO> allItems = itemService.getAllItems();
        return allItems;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable ("id") int itemId){
        try {
            itemService.deleteItem(itemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
