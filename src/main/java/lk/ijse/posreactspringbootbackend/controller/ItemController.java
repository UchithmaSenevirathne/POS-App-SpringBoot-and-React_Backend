package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.dto.UserDTO;
import lk.ijse.posreactspringbootbackend.exception.DataPersistFailedException;
import lk.ijse.posreactspringbootbackend.service.ItemService;
import lk.ijse.posreactspringbootbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestPart("unitPrice") double unitPrice,
            @RequestPart("itemQty") int itemQty,
            @RequestPart("itemImage") String itemImage
    ) {
        try {
            String base64ItemImg = AppUtil.toBase64ProfilePic(itemImage);

            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemName(itemName);
            itemDTO.setItemPrice(unitPrice);
            itemDTO.setItemQuantity(itemQty);
            itemDTO.setItemImage(base64ItemImg);

            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
