package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.CategoryDTO;
import lk.ijse.posreactspringbootbackend.dto.ItemDTO;
import lk.ijse.posreactspringbootbackend.service.CategoryService;
import lk.ijse.posreactspringbootbackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/category")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/items/{cat_id}")
    public List<ItemDTO> getItemsByCategory(@PathVariable int cat_id) {
        return itemService.getItemsByCategory(cat_id);
    }
}
