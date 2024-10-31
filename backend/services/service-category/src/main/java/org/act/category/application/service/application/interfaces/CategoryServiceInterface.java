package org.act.category.application.service.application.interfaces;

import org.act.category.application.service.application.dto.CategoryCreateDTO;
import org.act.category.application.service.application.dto.CategoryResponseDTO;
import org.act.category.application.service.application.dto.CategoryUpdateDTO;

import java.util.List;

public interface CategoryServiceInterface {
    CategoryResponseDTO createCategory(CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO);
    void deleteCategory(Long id);
    List<CategoryResponseDTO> listCategories();
    CategoryResponseDTO getCategoryById(Long id);
}

