package org.smart4j.chapter2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.utils.DataBaseHelper;


public class CustomerService {

	private static Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	/**
	 * //TODO 查询客户列表
	 * @return
	 */
	@Deprecated
	public List<Customer> getList() {
		List<Customer> list = new ArrayList<Customer>();
		Connection conn = null;
		try {
			conn = DataBaseHelper.getConnection();
			String sql = "select * from customer";
			PreparedStatement prep = conn.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				Customer cus = new Customer();
				cus.setName(rs.getString("name"));
				cus.setContact(rs.getString("contact"));
				cus.setEmail(rs.getString("email"));
				cus.setTelphone(rs.getString("telphone"));
				cus.setRemark(rs.getString("remark"));
				list.add(cus);
			}
		} catch (SQLException e) {
			logger.error("execute sql failure", e);
		} finally {
//			DataBaseHelper.closeConnection(conn);
		}
		return list;
	}
	
	public List<Customer> queryEntryList() {
		String sql = " select * from customer ";
			return DataBaseHelper.queryListEntry(Customer.class, sql);
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
