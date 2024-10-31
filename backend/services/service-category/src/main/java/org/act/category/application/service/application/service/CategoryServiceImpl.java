package org.act.category.application.service.application.service;

import org.act.category.application.service.application.dto.CategoryCreateDTO;
import org.act.category.application.service.application.dto.CategoryResponseDTO;
import org.act.category.application.service.application.dto.CategoryUpdateDTO;
import org.act.category.application.service.application.interfaces.CategoryServiceInterface;
import org.act.category.application.service.domain.entities.Category;
import org.act.category.application.service.infrastructure.interfaces.CategoryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryServiceInterface {

    @Autowired
    private CategoryRepositoryInterface categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryCreateDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return mapToResponseDTO(categoryRepository.save(category));
    }


    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return mapToResponseDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponseDTO> listCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return mapToResponseDTO(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}

