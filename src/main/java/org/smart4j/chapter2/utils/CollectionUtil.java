package org.smart4j.chapter2.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * 集合工具类
 * @author wangrt
 * @date 2017年2月15日
 * TODO
 *
 */
public final class CollectionUtil {

	/**
	 * 判断Collection是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}
	
	/**
	 * 判断Collection是否不为空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	/**
	 * 判断Map是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}
	
	/**
	 * 判断Map是否不为空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return isEmpty(map);
	}
}
