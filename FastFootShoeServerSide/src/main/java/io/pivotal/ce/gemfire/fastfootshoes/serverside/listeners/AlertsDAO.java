package io.pivotal.ce.gemfire.fastfootshoes.serverside.listeners;

import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AlertsDAO extends JdbcDaoSupport {

	private String insertSQL;
	private String createDB;
	private String createTable;

	public AlertsDAO(DataSource dataSource) {
		super.setDataSource(dataSource);
		createDataBaseAndTables();
	}

	public void insert(Alert alert) {
		insertSQL = "INSERT INTO fastfootshoes.message_table (message) VALUES (?)";
		getJdbcTemplate().update(insertSQL, new Object[] { alert.getMessage() });
	}

	private void createDataBaseAndTables() {
		createDB = "CREATE DATABASE IF NOT EXISTS fastfootshoes";
		getJdbcTemplate().update(createDB);
		createTable = "CREATE TABLE IF NOT EXISTS fastfootshoes.message_table (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, message VARCHAR(100))";
		getJdbcTemplate().update(createTable);
	}

	public String getInsertSQL() {
		return insertSQL;
	}

	public void setInsertSQL(String insertSQL) {
		this.insertSQL = insertSQL;
	}

	public String getCreateDB() {
		return createDB;
	}

	public void setCreateDB(String createDB) {
		this.createDB = createDB;
	}

	public String getCreateTable() {
		return createTable;
	}

	public void setCreateTable(String createTable) {
		this.createTable = createTable;
	}
	
	
	

}
