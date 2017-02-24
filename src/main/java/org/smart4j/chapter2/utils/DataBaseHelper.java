package org.smart4j.chapter2.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class DataBaseHelper {

	private static Logger logger = LoggerFactory.getLogger(DataBaseHelper.class);
	private static final QueryRunner QUERY_RUNNER;
	private static final BasicDataSource DATA_SOURCE;
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	
	private static final String DRIVER;
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		
		Properties props = PropUtil.getProperties("config.properties");
		DRIVER = PropUtil.getString(props, "jdbc.driver");
		URL = PropUtil.getString(props, "jdbc.url");
		USER = PropUtil.getString(props, "jdbc.user");
		PASSWORD = PropUtil.getString(props, "jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USER);
		DATA_SOURCE.setPassword(PASSWORD);
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("can not load jdbc driver", e);
		}
		
	}
	
	public static <T> List<T> queryListEntry(Class<T> cls, String sql) {
		List<T> entryList = null;
		Connection conn = getConnection();
		try {
			entryList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(cls));
		} catch (SQLException e) {
			logger.error("query list entry failure", e);
			throw new RuntimeException(e);
		}
		
		return entryList;
	}
	
	public static <T> T queryEntry(Class<T> cls, String sql, Object... params) {
		T entry;
		try {
			Connection conn = getConnection();
			entry = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(cls), params);
		} catch (SQLException e) {
			logger.error("query entry failure", e);
			throw new RuntimeException(e);
		} 
		return entry;
	}
	
	public static <T> Long insertEntry(Class<T> cls, Map<String, Object> fieldMap) {
		Long i = 0L;
		if(CollectionUtil.isNotEmpty(fieldMap)) {
			
			Connection conn = getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("insert into " + getTableName(cls));
			StringBuffer colSb = new StringBuffer("("); 
			StringBuffer valSb = new StringBuffer("("); 
			for(Entry<String, Object> field : fieldMap.entrySet()) {
				colSb.append(field.getKey()).append(",");
				valSb.append("?,");
			}
			sb.append(colSb.substring(0, colSb.lastIndexOf(","))).append(")").append(" values ")
				.append(valSb.substring(0, valSb.lastIndexOf(","))).append(")");
			try {
				i = QUERY_RUNNER.insert(conn, sb.toString(), new ScalarHandler<Long>(),
						fieldMap.values().toArray());
			} catch (SQLException e) {
				logger.error("insert entry failure", e);
				throw new RuntimeException(e);
			}
		}
		
		return i;
	}
	
	public static <T> Boolean updateEntry(Class<T> cls, Map<String, Object> fieldMap, Long id) {
		
		if(CollectionUtil.isNotEmpty(fieldMap)) {
			
			StringBuffer sb = new StringBuffer("update ").append(getTableName(cls)).append(" set ");
			for(Entry<String, Object> entry : fieldMap.entrySet()) {
				sb.append(entry.getKey()).append("=?").append(",");
			}
			
			sb = sb.delete(sb.lastIndexOf(","), sb.length());
			sb.append(" where id=").append(id);
			int i = executeSql(cls, sb.toString(), fieldMap.values().toArray() );
			
			if(i > 0) {
				return true;
			}
		} else {
			logger.error("can not update entry: fieldMap is emptry");
			return false;
		}
		
		return false;
		
	} 
	
	public static <T> Boolean deleteEntry(Class<T> cls, Long id) {
		String sql = " delete from "  + getTableName(cls) + " where id=?";
		return executeSql(cls, sql, id)==1;
		
	} 
	
	public static <T> int executeSql(Class<T> cls, String sql, Object... params) {
		Connection conn = getConnection();
		int row = 0;
		try {
			row = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			logger.error("update entry failure", e);
			throw new RuntimeException(e);
		} 
		
		return row;
	}
	
	public static <T> List<Map<String, Object>> executeQuery(Class<T> cls, String sql, Object... params) {
		
		List<Map<String, Object>> list = null;
		if(StringUtil.isNotEmpty(sql)) {
			Connection conn = getConnection();
			
			try {
				list = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
			} catch (SQLException e) {
				logger.error("query list failure", e);
				throw new RuntimeException(e);
			}
		} else {
			logger.error("sql is empty");
		}
		return list;
	}
	
	public static <T> String getTableName(Class<T> cls) {
		return cls.getSimpleName().toLowerCase();
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = CONNECTION_HOLDER.get();
			if(conn == null) {
				conn = DATA_SOURCE.getConnection();
			}
		} catch (SQLException e) {
			logger.error("create connection failure", e);
			throw new RuntimeException(e);
		} finally {
			CONNECTION_HOLDER.set(conn);
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
}
