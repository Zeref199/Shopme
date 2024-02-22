package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandRepository repo;

    public List<Brand> ListAll(){
        return (List<Brand>) repo.findAll();
    }


}
