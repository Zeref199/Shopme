package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category  c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);

    public Category findByName(String name);

    public Category findByAlias(String alias);

}
