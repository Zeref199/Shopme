package com.shopme.admin.product.controller;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.product.Product;
import com.shopme.common.entity.product.ProductImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProductSaveHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSaveHelper.class);

    static void deleteExtraImagesRemovedFromForm(Product product) {
        String extraImageDir = "../product-images/" + product.getId() + "/extras";
        Path dirPath = Paths.get(extraImageDir);

        try{
            Files.list(dirPath).forEach(file->{
                String fileName= file.toFile().getName();

                if(!product.containsImageName(fileName)){
                    try{
                        Files.delete(file);
                        LOGGER.info("Deleted extra image: " + fileName);
                    }catch(IOException e){
                        LOGGER.error("Could not delete extra image: " + fileName);
                    }
                }
            });
        }catch(IOException e){
            LOGGER.error("Could not list directory: " + dirPath);
        }
    }

    static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, Product product) {
        if(imageIDs == null || imageIDs.length == 0) return;

        Set<ProductImage> images = new HashSet<>();
        for(int count = 0; count < imageIDs.length; count++){
            Integer id = Integer.parseInt(imageIDs[count]);
            String name = imageNames[count];

            images.add(new ProductImage(id, name, product));
        }
        product.setImages(images);
    }

    static void setProductDetails(String[] detailIDs, String[] detailNames, String[] detailValues, Product product) {
        if(detailNames == null || detailNames.length == 0) return;

        for(int count = 0 ; count < detailNames.length; count++){
            String name = detailNames[count];
            String value = detailValues[count];
            Integer id = Integer.parseInt(detailIDs[count]);

            if(id != 0){
                product.addDetails(id, name, value);
            }else if(!name.isEmpty() && !value.isEmpty()){
                product.addDetails(name, value);
            }
        }
    }

    static void saveUploadedImages(MultipartFile mainImageMultipart, MultipartFile[] extraImageMultipart, Product saveProduct) throws IOException {
        if(!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(mainImageMultipart.getOriginalFilename()));
            String uploadDir = "../product-images/" + saveProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
        }

        if(extraImageMultipart.length > 0) {
            String uploadDir = "../product-images/" + saveProduct.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageMultipart) {
                if (multipartFile.isEmpty()) continue;

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }
    }

    static void setNewExtraImageNames(MultipartFile[] extraImageMultipart, Product product) {
        if(extraImageMultipart.length > 0) {
            for (MultipartFile multipartFile : extraImageMultipart) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                    if(!product.containsImageName(fileName)) {
                        product.addExtraImage(fileName);
                    }
                }
            }
        }
    }

    static void setMainImageName(MultipartFile mainImageMultipart, Product product){
        if(!mainImageMultipart.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(mainImageMultipart.getOriginalFilename()));
            product.setMainImage(fileName);
        }
    }
}
