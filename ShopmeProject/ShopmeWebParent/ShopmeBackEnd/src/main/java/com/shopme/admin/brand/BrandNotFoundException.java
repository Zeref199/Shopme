package com.shopme.admin.brand;

import java.io.IOException;

public class BrandNotFoundException extends IOException {
    public BrandNotFoundException(String message) {
        super(message);
    }
}
