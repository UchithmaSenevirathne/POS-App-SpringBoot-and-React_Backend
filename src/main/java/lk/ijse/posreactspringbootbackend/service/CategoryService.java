package lk.ijse.posreactspringbootbackend.service;

import lk.ijse.posreactspringbootbackend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}
