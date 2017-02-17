package org.smart4j.chapter2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataBaseHelper {

	private static Logger logger = LoggerFactory.getLogger(DataBaseHelper.class);
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	
	private static final String DRIVER;
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	static {
		Properties props = PropUtil.getProperties("config.properties");
		DRIVER = PropUtil.getString(props, "jdbc.driver");
		URL = PropUtil.getString(props, "jdbc.url");
		USER = PropUtil.getString(props, "jdbc.user");
		PASSWORD = PropUtil.getString(props, "jdbc.password");
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
		} finally {
			closeConnection();
		}
		return entryList;
	}
	
	public static <T> T queryEntry(Class<T> cls, String sql, Object... params) {
		T entry;
		try {
			Connection conn = CONNECTION_HOLDER.get();
			entry = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(cls), params);
		} catch (SQLException e) {
			logger.error("query entry failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return entry;
	}
	
	public static <T> T insertEntry(Class<T> cls, Map<String, Object> fieldMap) {
		T entry = null;
		Connection conn = CONNECTION_HOLDER.get();
		if(CollectionUtil.isNotEmpty(fieldMap)) {
			
			StringBuffer sb = new StringBuffer();
			sb.append("insert into " + getTableName(cls.getName()));
			StringBuffer colSb = new StringBuffer("("); 
			StringBuffer valSb = new StringBuffer("("); 
			for(Entry<String, Object> field : fieldMap.entrySet()) {
				colSb.append(field.getKey()).append(",");
				valSb.append("?,");
			}
			sb.append(colSb.substring(0, colSb.lastIndexOf(","))).append(")")
				.append(valSb.substring(0, valSb.lastIndexOf(","))).append(")");
			try {
				entry = QUERY_RUNNER.insert(conn, sb.toString(), new BeanHandler<T>(cls),
						fieldMap.values().toArray());
			} catch (SQLException e) {
				logger.error("insert entry failure", e);
				throw new RuntimeException(e);
			}
		}
		
		return entry;
	}
	
	public static String getTableName(String tableName) {
		return tableName.toLowerCase();
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = CONNECTION_HOLDER.get();
			if(conn == null) {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
