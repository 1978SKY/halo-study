package com.halo.service.base;

import com.halo.repository.base.BaseRepository;
import com.halo.utils.ObjectUtils;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Deep
 * @create 2023-09-27
 */
@Slf4j
public class AbstractCrudService<Entity, ID> implements CrudService<Entity, ID> {

	private final String entityName;

	private final BaseRepository<Entity, ID> repository;

	protected AbstractCrudService(BaseRepository<Entity, ID> repository) {
		this.repository = repository;
		// Get domain name
		@SuppressWarnings("unchecked")
		Class<Entity> domainClass = (Class<Entity>) fetchType(0);
		entityName = domainClass.getSimpleName();
	}


	/**
	 * 获取泛型类信息
	 *
	 * @param index generic type index
	 * @return real generic type will be returned
	 */
	private Type fetchType(int index) {
		Assert.isTrue(index >= 0 && index <= 1, "type index must be between 0 to 1");

		return ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[index];
	}

	@Override
	public List<Entity> listAll() {
		return repository.findAll();
	}

	@Override
	public Entity getById(ID id) {
		Assert.notNull(id, "id must not null!");

		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Entity> finByIds(Collection<ID> ids) {
		Assert.notEmpty(ids, "ids must not null!");

		return repository.findAllById(ids);
	}


	@Override
	public List<Entity> saveBatch(Collection<Entity> entities) {
		Assert.notEmpty(entities, "entities must not empty!");

		return repository.saveAll(entities);
	}

	@Override
	public Entity save(Entity entity) {
		Assert.notNull(entity, "entity must not null!");

		return repository.save(entity);
	}

	@Override
	public Entity update(Entity entity) {
		Assert.notNull(entity, "entity must not null!");

		Entity entityDb = getById(getId(entity));
		if (Objects.isNull(entityDb)) {
			throw new IllegalArgumentException("更新失败");
		}
		ObjectUtils.convert(entity, entityDb, true);
		return repository.saveAndFlush(entityDb);
	}

	@Override
	public List<Entity> updateBatch(List<Entity> entities) {
		Assert.notEmpty(entities, "entities must not null!");

		List<Entity> entitiesDb = finByIds(getIds(entities));
		if (CollectionUtils.isEmpty(entitiesDb) || entitiesDb.size() != entities.size()) {
			throw new IllegalArgumentException("更新失败");
		}
		ObjectUtils.convertList(entities, entitiesDb, true);

		return repository.saveAll(entitiesDb);
	}


	/**
	 * 通过反射获取实体中的主键
	 */
	public ID getId(Entity entity) {
		Assert.notNull(entity, "entity must not null!");
		Field[] declaredFields = entity.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			// 检查字段是否标有@Id注解
			if (field.isAnnotationPresent(Id.class)) {
				// 设置字段可访问，以便获取其值
				field.setAccessible(true);

				try {
					@SuppressWarnings("unchecked")
					ID value = (ID) field.get(entity);
					return value;
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new IllegalArgumentException("该实体不存在主键Id");
	}

	public List<ID> getIds(List<Entity> entities) {
		Assert.notEmpty(entities, "entities must not empty!");
		return entities.stream().map(this::getId).toList();
	}

}
