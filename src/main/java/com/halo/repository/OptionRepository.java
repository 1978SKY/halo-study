package com.halo.repository;

import com.halo.model.entity.OptionEntity;
import com.halo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Deep
 * @create 2023-09-27
 */
public interface OptionRepository extends BaseRepository<OptionEntity,Integer>, JpaSpecificationExecutor<OptionEntity> {
}
