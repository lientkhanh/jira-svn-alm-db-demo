package com.kintosoft.svnwebclient.jira.public_interface;

import java.sql.Connection;
import java.sql.SQLException;

public interface SWCPublicInterface {
	public Connection getConnection() throws SQLException;
}
