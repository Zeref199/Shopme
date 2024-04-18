package com.shopme.common.entity.section;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.IdBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sections_brands")
public class BrandSection extends IdBasedEntity {
    @Column(name = "brand_order")
    private int brandOrder;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public int getBrandOrder() {
        return brandOrder;
    }

    public void setBrandOrder(int brandOrder) {
        this.brandOrder = brandOrder;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
