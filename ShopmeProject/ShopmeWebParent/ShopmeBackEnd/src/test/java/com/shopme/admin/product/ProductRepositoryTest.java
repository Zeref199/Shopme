package com.shopme.admin.product;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void CreateNewProduct(){
       Brand brand = entityManager.find(Brand.class, 37);
       Category category = entityManager.find(Category.class, 5);

       Product product = new Product();
       product.setName("Acer Aspire Desktop");
       product.setAlias("acer_aspire_desktop");
       product.setShortDescription("this is a short description for Acer");
       product.setFullDescription("this is a full description for Acer");

       product.setCategory(category);
       product.setBrand(brand);

       product.setPrice(678);
       product.setCost(600);
       product.setEnabled(true);
       product.setInStock(true);

       product.setCreatedTime(new Date());
       product.setUpdatedTime(new Date());

       Product savedProduct = repo.save(product);

       assertThat(savedProduct).isNotNull();
       assertThat(savedProduct.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllProducts(){
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }

    @Test
    public void GetProduct(){
        Integer id =2;
        Product product = repo.findById(id).get();
        System.out.println(product);
        assertThat(product).isNotNull();
    }

    @Test
    public void UpdateProduct(){
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(499);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(499);
    }

    @Test
     public void testDeleteProduct(){
        Integer id = 3;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(!result.isPresent());
    }

}
