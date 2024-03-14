package com.shopme.admin.paging;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
@Retention(RetentionPolicy.RUNTIME)
@Target(PARAMETER)
public @interface PagingAndSortingParam {
    public String moduleURL();

    public String listName();
}
