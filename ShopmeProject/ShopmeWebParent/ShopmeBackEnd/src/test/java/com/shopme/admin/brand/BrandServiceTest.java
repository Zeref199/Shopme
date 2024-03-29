package com.shopme.admin.brand;

import com.shopme.admin.brand.repo.BrandRepository;
import com.shopme.admin.brand.service.BrandService;
import com.shopme.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTest {

    @MockBean
    private BrandRepository repo;
    @InjectMocks
    private BrandService service;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicate(){
        Integer id = null;
        String name = "Acer";
        Brand brand = new Brand(name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);
        assertThat(result).isEqualTo("Duplicate");
    }
}
