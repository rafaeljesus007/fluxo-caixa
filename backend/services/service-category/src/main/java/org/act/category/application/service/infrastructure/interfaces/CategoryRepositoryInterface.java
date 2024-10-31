package org.act.category.application.service.infrastructure.interfaces;

import org.act.category.application.service.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryInterface extends JpaRepository<Category, Long> {
}

