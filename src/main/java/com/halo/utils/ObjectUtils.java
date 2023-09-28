package com.halo.utils;

import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Deep
 * @create 2023-09-28
 */
public class ObjectUtils {

//	static ExecutorService executor;
//	static {
//		int numThreads = Runtime.getRuntime().availableProcessors(); // 获取可用的处理器核心数
//		executor = Executors.newFixedThreadPool(numThreads); // 创建固定数量的线程池
//	}

	/**
	 * @param source            源对象
	 * @param target            目标对象
	 * @param isIgnoreNullField 是否忽略空字段。如果忽略，source中为空的值将不会赋值到target
	 */
	public static void convert(Object source, Object target, boolean isIgnoreNullField) {
		if (isIgnoreNullField) {
			copyNonNullFields(source, target);
		} else {
			convert(source, target);
		}
	}


	public static <T> void convertList(List<T> sources, List<T> targets, boolean isIgnoreNullField) {
		if (sources.size() != targets.size()) {
			throw new IllegalArgumentException("sources size must equals targets size!");
		}

		for (int i = 0; i < sources.size(); i++) {
			if (isIgnoreNullField) {
				copyNonNullFields(sources.get(i), targets.get(i));
			} else {
				convert(sources.get(i), targets.get(i));
			}
		}
	}

	/**
	 * @param source 源对象
	 * @param target 目标对象
	 */
	public static void convert(Object source, Object target) {
		BeanUtil.copyProperties(source, target);
	}


	private static void copyNonNullFields(Object source, Object target) {
		Class<?> sourceClass = source.getClass();
		Class<?> targetClass = target.getClass();

		Field[] fields = sourceClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			try {
				Object fieldValue = field.get(source);
				if (fieldValue != null) {
					Field destinationField = targetClass.getDeclaredField(field.getName());
					destinationField.setAccessible(true);
					destinationField.set(target, fieldValue);
				}
			} catch (IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}

}
