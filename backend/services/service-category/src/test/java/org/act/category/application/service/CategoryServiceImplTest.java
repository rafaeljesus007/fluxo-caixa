package org.act.category.application.service;

import org.act.category.application.service.application.dto.CategoryCreateDTO;
import org.act.category.application.service.application.dto.CategoryDTO;
import org.act.category.application.service.application.dto.CategoryResponseDTO;
import org.act.category.application.service.application.dto.CategoryUpdateDTO;
import org.act.category.application.service.application.service.CategoryServiceImpl;
import org.act.category.application.service.domain.entities.Category;
import org.act.category.application.service.infrastructure.repository.CategoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;
    private CategoryRepositoryImpl categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepositoryImpl.class);
        categoryService = new CategoryServiceImpl();
    }

    @Test
    void testCreateCategory() {
        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName("Food");

        Category category = new Category();
        category.setId(1L);
        category.setName(categoryCreateDTO.getName());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO result = categoryService.createCategory(categoryCreateDTO);

        assertNotNull(result);
        assertEquals(categoryCreateDTO.getName(), result.getName());
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        CategoryUpdateDTO categoryDTO = new CategoryUpdateDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Updated Name");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        CategoryResponseDTO result = categoryService.updateCategory(categoryId, categoryDTO);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals(categoryDTO.getName(), result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testListCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponseDTO> result = categoryService.listCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponseDTO result = categoryService.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }
}

