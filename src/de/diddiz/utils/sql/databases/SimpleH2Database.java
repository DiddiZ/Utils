package de.diddiz.utils.sql.databases;

import java.io.File;
import java.sql.SQLException;

/**
 * @author Robin Kupper
 */
public abstract class SimpleH2Database extends SimpleDatabase
{
	public SimpleH2Database(File databaseFile) throws SQLException {
		this(databaseFile, "", "");
	}

	public SimpleH2Database(File databaseFile, String user, String pw) throws SQLException {
		super("org.h2.Driver", "jdbc:h2:" + databaseFile.getPath(), user, pw);
	}
}
