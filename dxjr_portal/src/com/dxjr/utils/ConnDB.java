package com.dxjr.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnDB {
	private static Connection conn = null;

	private static final String DRIVER_NAME = "";

	private static final String URL = "";

	private static final String USER_NAME = "";

	private static final String PASSWORD = "";
	
	/**
	 * 数据库连接工具类——仅仅获得连接对象
	 * 
	 */
	public static Connection getConn() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
