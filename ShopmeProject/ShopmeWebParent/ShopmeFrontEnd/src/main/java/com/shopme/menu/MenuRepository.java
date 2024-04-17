package com.shopme.menu;

import com.shopme.common.entity.Menu;
import com.shopme.common.entity.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    public List<Menu> findByTypeAndEnabledOrderByPositionAsc(MenuType type, boolean enabled);

    @Query("Select m FROM Menu m WHERE m.alias = ?1 AND m.enabled = true")
    public Menu findByAlias(String alias);
}
