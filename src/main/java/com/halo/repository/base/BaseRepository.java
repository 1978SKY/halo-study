package com.halo.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Deep
 * @create 2023-09-27
 */
@NoRepositoryBean
public interface BaseRepository<Entity, ID> extends JpaRepository<Entity, ID> {
}
