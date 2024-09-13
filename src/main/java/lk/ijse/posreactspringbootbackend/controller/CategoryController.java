package lk.ijse.posreactspringbootbackend.controller;

import lk.ijse.posreactspringbootbackend.dto.CategoryDTO;
import lk.ijse.posreactspringbootbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend/category")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
