package com.shopme.admin.product.repo;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends SearchRepository<Product, Integer> {

    public Product findByName(String name);

    public Long countById(Integer id);

    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
                + "OR p.shortDescription LIKE %?1%"
                + "OR p.fullDescription LIKE %?1%"
                + "OR p.brand.name LIKE %?1%"
                + "OR p.category.name LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId OR p.category.allParentIds LIKE %:categoryIdMatch%")
    public Page<Product> findAllInCategory(@Param("categoryId") Integer categoryId, @Param("categoryIdMatch") String categoryIdMatch, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (p.category.id = :categoryId OR p.category.allParentIds LIKE %:categoryIdMatch%) AND"
            + " (p.name LIKE %:keyword% OR p.shortDescription LIKE %:keyword% OR p.fullDescription LIKE %:keyword% OR p.brand.name LIKE %:keyword% OR p.category.name LIKE %:keyword%)")

    public Page<Product> searchInCategory(@Param("categoryId") Integer categoryId,
                                           @Param("categoryIdMatch") String categoryIdMatch,
                                           @Param("keyword") String keyword ,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public Page<Product> searchProductsByName(String keyword, Pageable pageable);

    @Query("Update Product p SET p.averageRating = COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.product.id = ?1), 0),"
            + " p.reviewCount = (SELECT COUNT(r.id) FROM Review r WHERE r.product.id =?1) "
            + "WHERE p.id = ?1")
    @Modifying
    public void updateReviewCountAndAverageRating(Integer productId);

}
