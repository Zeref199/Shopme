package com.shopme.common.entity.section;

import com.shopme.common.entity.IdBasedEntity;
import jakarta.persistence.*;

import com.shopme.common.entity.Category;

@Entity
@Table(name = "sections_categories")
public class CategorySection extends IdBasedEntity {
    @Column(name = "category_order")
    private int categoryOrder;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
