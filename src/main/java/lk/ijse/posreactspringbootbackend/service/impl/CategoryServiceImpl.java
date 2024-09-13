package lk.ijse.posreactspringbootbackend.service.impl;

import lk.ijse.posreactspringbootbackend.dao.CategoryDAO;
import lk.ijse.posreactspringbootbackend.dto.CategoryDTO;
import lk.ijse.posreactspringbootbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getCat_id(), category.getCat_name()))
                .collect(Collectors.toList());
    }
}
