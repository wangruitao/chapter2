package org.smart4j.chapter2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.smart4j.chapter2.DbUtilsExample.TableUnderLineToBeanDbUtil;
import org.smart4j.chapter2.model.Users;

import junit.framework.Assert;

public class TableUnderLineToBeanDbUtilTest {

	
	@Test
	public void getUserTest() {
		Users user = TableUnderLineToBeanDbUtil.getUser(1);
		System.out.println(user);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void getUsersTest(){
		List<Users> users = TableUnderLineToBeanDbUtil.getUsers();
		System.out.println(users);
		Assert.assertEquals(3, users.size());
	}
	
	@Test
	public void getUsersCountTest() {
		Long count = TableUnderLineToBeanDbUtil.getUsersCount();
		System.out.println("=============== count:" + count);
		Assert.assertNotNull(count);
		Assert.assertEquals(3L, count.longValue());
	}
	
	@Test
	public void insertScalarUserTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_name", "java程序编写");
		map.put("login_name", "javahello");
		map.put("user_password", "sefsfsfwew");
		map.put("user_level", 15);
		map.put("user_lock", 1);
		Long id = TableUnderLineToBeanDbUtil.insertScalarUser(map);
		System.out.println(" insert user id : " + id);
	}
	
	@Test
	public void getArrayUserTest() {
		Object[] obja = TableUnderLineToBeanDbUtil.getArrayUser(3L);
		System.out.println(obja);
	}
	
	@Test
	public void insertArrayUserTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_name", "java程序编写");
		map.put("login_name", "javahello");
		map.put("user_password", "sefsfsfwew");
		map.put("user_level", 15);
		map.put("user_lock", 1);
		Object[] obja = TableUnderLineToBeanDbUtil.insertArrayUser(map);
		System.out.println(" insert user object : " + obja);
	}
	
	@Test
	public void findIrregularFieldNameTest() {
		List<Users> ulist = TableUnderLineToBeanDbUtil.findIrregularFieldName();
		Assert.assertEquals(5, ulist.size());
	}
	
	@Test
	public void findIrregularFieldNameMapTest() {
		Users user = TableUnderLineToBeanDbUtil.findIrregularFieldNameMap();
		Assert.assertNotNull(user);
	}
	
	@Test
	public void getUsersMapTest() {
		Map<String, Object> user = TableUnderLineToBeanDbUtil.getUsersMap();
		Assert.assertNotNull(user);
	}
	
}
