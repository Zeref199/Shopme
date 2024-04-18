package com.shopme.section;

import com.shopme.common.entity.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    public List<Section> findAllByEnabledOrderBySectionOrderAsc(boolean enabled);
}
