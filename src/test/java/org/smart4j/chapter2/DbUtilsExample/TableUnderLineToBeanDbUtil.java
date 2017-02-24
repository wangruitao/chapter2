package org.smart4j.chapter2.DbUtilsExample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Users;
import org.smart4j.chapter2.utils.DataBaseHelper;

public class TableUnderLineToBeanDbUtil {

	private static final Logger logger = LoggerFactory.getLogger(TableUnderLineToBeanDbUtil.class);
	private static final QueryRunner QUERY_RUNNER;
	
	static {
		QUERY_RUNNER = new QueryRunner();
	}
	
	public static Users getUser(Integer id) {
		Connection conn = DataBaseHelper.getConnection();
		Users user = null;
		String sql = " select * from users where id=?";
		BeanProcessor bp = new GenerousBeanProcessor();
		BeanHandler<Users> rs = new BeanHandler<Users>(Users.class, new BasicRowProcessor(bp)) ;
		try {
			user = QUERY_RUNNER.query(conn, sql, rs, id);
		} catch (SQLException e) {
			logger.error("query entry list failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		
		return user;
	}
	
	public static List<Users> getUsers() {
		Connection conn = DataBaseHelper.getConnection();
		List<Users> list = null;
		String sql = " select * from users";
		BeanProcessor bp = new GenerousBeanProcessor();
		BeanListHandler<Users> rs = new BeanListHandler<Users>(Users.class, new BasicRowProcessor(bp)) ;
		try {
			list = QUERY_RUNNER.query(conn, sql, rs);
		} catch (SQLException e) {
			logger.error("query entry list failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		
		return list;
	}
	
	public static Long getUsersCount() {
		Connection conn = DataBaseHelper.getConnection();
		String sql = " select count(1) from users";
		Long count = null;
		try {
			count = QUERY_RUNNER.query(conn, sql, new ScalarHandler<Long>());
		} catch (SQLException e) {
			logger.error("query count failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		return count;
	}
	
	public static Long insertScalarUser(Map<String, Object> fieldMap) {
		Connection conn = DataBaseHelper.getConnection();
		StringBuffer sqlSb = new StringBuffer(" insert into users ");
		StringBuffer fieldSb =  new StringBuffer(" ( ");
		StringBuffer valueSb =  new StringBuffer(" values ( ");
		
		for(Entry<String, Object> entry : fieldMap.entrySet()) {
			fieldSb.append(entry.getKey()).append(",");
			valueSb.append("?,");
		}
		
		sqlSb.append(fieldSb.delete(fieldSb.lastIndexOf(","), fieldSb.length())).append(")")
		.append(valueSb.delete(valueSb.lastIndexOf(","), valueSb.length())).append(")");
		Long id = null;
		try {
			id = QUERY_RUNNER.insert(conn, sqlSb.toString(), new ScalarHandler<Long>(), fieldMap.values().toArray());
		} catch (SQLException e) {
			logger.error("insert user failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		
		return id;
	}
	
	public static Object[] getArrayUser(Long Id) {
		Connection conn = DataBaseHelper.getConnection();
		String sql = " select * from users where id=?"; 
		Object[] obs = null;
		try {
			obs = QUERY_RUNNER.query(conn, sql, new ArrayHandler(new BasicRowProcessor( new GenerousBeanProcessor())), Id);
		} catch (SQLException e) {
			logger.error("query array user failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		
		return obs;
	}
	
	public static Object[] insertArrayUser(Map<String, Object> fieldMap) {
		Connection conn = DataBaseHelper.getConnection();
		StringBuffer sqlSb = new StringBuffer(" insert into users ");
		StringBuffer fieldSb =  new StringBuffer(" ( ");
		StringBuffer valueSb =  new StringBuffer(" values ( ");
		
		for(Entry<String, Object> entry : fieldMap.entrySet()) {
			fieldSb.append(entry.getKey()).append(",");
			valueSb.append("?,");
		}
		
		sqlSb.append(fieldSb.delete(fieldSb.lastIndexOf(","), fieldSb.length())).append(")")
		.append(valueSb.delete(valueSb.lastIndexOf(","), valueSb.length())).append(")");
		Object[]  obja = null;
		try {
			obja = QUERY_RUNNER.insert(conn, sqlSb.toString(), new ArrayHandler(), fieldMap.values().toArray());
		} catch (SQLException e) {
			logger.error("insert user failure", e);
			throw new RuntimeException(e);
		} finally {
			DataBaseHelper.closeConnection();
		}
		
		return obja;
	}
	
	public static List<Users> findIrregularFieldName() {
		List<Users> list = null;
		Connection conn = DataBaseHelper.getConnection();
		String sql = " select * from users_m";
		Map<String, String> map = new HashMap<String, String>();
		map.put("Id", "id");
		map.put("cs_user_name", "userName");
		map.put("cs_login_name", "loginName");
		map.put("cs_user_password", "userPassword");
		map.put("cs_user_level", "userLevel");
		map.put("cs_user_level", "userLock");
		try {
			list = QUERY_RUNNER.query(conn, sql, new BeanListHandler<Users>(Users.class, new BasicRowProcessor(new BeanProcessor(map))));
		} catch (SQLException e) {
			logger.error("find irregular entry failure", e);
			throw new RuntimeException(e);
		} finally{
			DataBaseHelper.closeConnection();
		}
		return list;
	}
	
	public static Users findIrregularFieldNameMap() {
		Users user = null;
		Connection conn = DataBaseHelper.getConnection();
		String sql = " select * from users_m";
		Map<String, String> map = new HashMap<String, String>();
		map.put("Id", "id");
		map.put("cs_user_name", "userName");
		map.put("cs_login_name", "loginName");
		map.put("cs_user_password", "userPassword");
		map.put("cs_user_level", "userLevel");
		map.put("cs_user_level", "userLock");
		try {
			user = QUERY_RUNNER.query(conn, sql, new BeanHandler<Users>(Users.class, new BasicRowProcessor(new BeanProcessor(map))));
		} catch (SQLException e) {
			logger.error("find irregular entry failure", e);
			throw new RuntimeException(e);
		} finally{
			DataBaseHelper.closeConnection();
		}
		return user;
	}
	
	public static Map<String, Object> getUsersMap() {
		Map<String, Object> resultMap = null;
		Connection conn = DataBaseHelper.getConnection();
		String sql = " select * from users_m";
		Map<String, String> map = new HashMap<String, String>();
		map.put("Id", "id");
		map.put("cs_user_name", "userName");
		map.put("cs_login_name", "loginName");
		map.put("cs_user_password", "userPassword");
		map.put("cs_user_level", "userLevel");
		map.put("cs_user_level", "userLock");
		try {
			resultMap = QUERY_RUNNER.query(conn, sql, new MapHandler(new BasicRowProcessor(new BeanProcessor(map))));
		} catch (SQLException e) {
			logger.error("find irregular entry failure", e);
			throw new RuntimeException(e);
		} finally{
			DataBaseHelper.closeConnection();
		}
		return resultMap;
	}
}
