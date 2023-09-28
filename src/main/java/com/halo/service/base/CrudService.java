package com.halo.service.base;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author Deep
 * @create 2023-09-27
 */
public interface CrudService<Entity, ID> {

	@NonNull
	List<Entity> listAll();

	@Nullable
	Entity getById(@NonNull ID id);

	@Nullable
	List<Entity> finByIds(@NonNull Collection<ID> ids);

	@NonNull
	List<Entity> saveBatch(@NonNull Collection<Entity> entities);

	@NonNull
	Entity save(@NonNull Entity entity);

	@NonNull
	Entity update(@NonNull Entity entity);

	@NonNull
	List<Entity> updateBatch(@NonNull List<Entity> entities);


}
