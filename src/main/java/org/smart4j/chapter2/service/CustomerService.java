package org.smart4j.chapter2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.smart4j.chapter2.model.Customer;

public class CustomerService {

	/**
	 * //TODO 查询客户列表
	 * @return
	 */
	public List<Customer> getList() {
		List<Customer> list = new ArrayList<Customer>();
		
		return list;
	}
	
	/**
	 * //TODO 根据id查找客户
	 * @param id
	 * @return
	 */
	public Customer getById(Integer id) {
		Customer customer = new Customer();
		
		return customer;
	}
	
	/**
	 * 	//TODO 创建客户
	 * @param map
	 * @return
	 */
	public boolean createCustomer(Map<String, Object> map) {
		return false;
	}
	
	/**
	 * 	//TODO 更新客户
	 * @param map
	 * @param id
	 * @return
	 */
	public boolean updateCustomer(Map<String, Object> map, Integer id) {
		return false;
	}
	
	/**
	 * 	//TODO 删除客户
	 * @param id
	 * @return
	 */
	
	public boolean deleteCustomer(Integer id) {
		return false;
	}
	
}
