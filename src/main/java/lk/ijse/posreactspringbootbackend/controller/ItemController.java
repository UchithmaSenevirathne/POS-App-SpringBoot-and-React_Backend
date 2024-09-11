package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/item")
@CrossOrigin
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private final ItemService itemService;
}
