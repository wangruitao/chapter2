package org.smart4j.chapter2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

public class CustomerServiceTest {

	private final CustomerService customerService;
	
	public CustomerServiceTest() {
		customerService = new CustomerService();
	}
	
	@Before
	public void init() {
		//TODO 数据库初始化
	}
	
	@Test
	public void getListTest() {
		List<Customer> list = customerService.getList();
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void queryListTest() {
		List<Customer> list = customerService.queryEntryList();
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void getByIdTest() {
		Customer customer = customerService.getById(1);
		Assert.assertNull(customer);
	}
	
	@Test
	public void createCustomerTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "customer3");
		map.put("contact", "Ilune");
		map.put("email", "ilune@gmail.com");
		map.put("telphone", "13534567891");
		map.put("remark", "");
		boolean suc = customerService.createCustomer(map);
		Assert.assertTrue(suc);
	}
	
	@Test
	public void updateCustomerTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", "ilune@qq.com");
		Integer id = 3;
		boolean suc = customerService.updateCustomer(map, id);
		Assert.assertTrue(suc);
	}
	
	@Test
	public void deleteCustomerTest() {
		Integer id = 3;
		boolean suc = customerService.deleteCustomer(id);
		Assert.assertTrue(suc);
	}
	
}
