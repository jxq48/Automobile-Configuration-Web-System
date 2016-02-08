package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class InitialDB extends ConnectDB {

	/* Initialize database and create AUTOMOBILE, OPTIONSET, OPT Tables */
	public void run() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
		try {
			/*
			 * Create three tables such that SQL is read from a text file, split
			 * SQL by ";"
			 */
			try {
				FileReader file = new FileReader("DBschema.txt");
				BufferedReader buff = new BufferedReader(file);
				String line;
				StringBuffer data = new StringBuffer();
				try {
					while ((line = buff.readLine()) != null)
						data.append(line);
					StringTokenizer result = new StringTokenizer(
							data.toString(), ";");
					while (result.hasMoreTokens()) {
						executeUpdate(conn, result.nextToken());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			conn.close();

		} catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
	}

}
