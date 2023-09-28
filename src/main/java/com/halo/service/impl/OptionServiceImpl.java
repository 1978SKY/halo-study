package com.halo.service.impl;

import com.halo.model.entity.OptionEntity;
import com.halo.repository.OptionRepository;
import com.halo.service.OptionService;
import com.halo.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Deep
 * @create 2023-09-27
 */
@Slf4j
@Service
public class OptionServiceImpl extends AbstractCrudService<OptionEntity, Integer> implements OptionService {

	private final OptionRepository optionRepository;

	protected OptionServiceImpl(OptionRepository optionRepository) {
		super(optionRepository);
		this.optionRepository = optionRepository;
	}
}
