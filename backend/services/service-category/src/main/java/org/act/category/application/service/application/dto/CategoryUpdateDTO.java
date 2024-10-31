package org.act.category.application.service.application.dto;


import jakarta.validation.constraints.NotBlank;

public class CategoryUpdateDTO {

    private Long id;  // Campo necessário para identificar a categoria a ser atualizada

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

