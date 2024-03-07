package com.shopme.admin.setting;

import com.shopme.common.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    public List<Currency> findAllByOrderByNameAsc();
}
