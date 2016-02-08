package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import adapter.AutoDB;
import model.Automobile;
import model.OptionSet;

public class UpdateTable extends ConnectDB implements AutoDB {
	/* Read SQL from a Property file */
	public Properties getProperty() {
		Properties props = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream("DBupdate.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/*
	 * Insert a new Automobile to AUTOMOBILE Table. OptionSet and corresponding
	 * Options are added into OPTIONSET Table and OPT Table
	 */
	public void insertDB(Automobile a1) {
		String AutoName = a1.getModel();
		float baseprice = a1.getBaseprice();
		// get sql from the file
		Properties props = getProperty();
		String insertAuto = props.getProperty("insertAuto");
		String insertOpset = props.getProperty("insertOpset");
		String insertOpt = props.getProperty("insertOpt");
		// Replace tmp strings in sql with values
		String command1 = insertAuto.replace("tmp1", AutoName).replace("tmp2",
				String.valueOf(baseprice));
		int OptsetSize = a1.getOpsetSize();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = getConnection("AUTOS");
			stmt = conn.createStatement();
			// get the generated AutoID
			stmt.executeUpdate(command1, Statement.RETURN_GENERATED_KEYS);
			int AutoID = 0;
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				AutoID = rs.getInt(1);
			}
			rs.close();
			// add all the optionsets to the OPTIONSET table.
			for (int i = 0; i < OptsetSize; i++) {
				OptionSet opset = a1.getOpset(i);
				String OptsetName = a1.getOpsetName(opset);
				String command2 = insertOpset.replace("tmp1", OptsetName)
						.replace("tmp2", String.valueOf(AutoID));
				// get the generated OptsetID
				stmt.executeUpdate(command2, Statement.RETURN_GENERATED_KEYS);
				int OptsetID = 0;
				ResultSet rs1 = stmt.getGeneratedKeys();
				if (rs1.next()) {
					OptsetID = rs1.getInt(1);
				}
				rs1.close();
				// add all the options to the OPT table
				int OptSize = a1.getOptSize(opset);
				for (int j = 0; j < OptSize; j++) {
					String OptName = a1.getOptName(j, opset);
					float OptPrice = a1.getOptPrice(opset, OptName);
					String command3 = insertOpt.replace("tmp1", OptName)
							.replace("tmp2", String.valueOf(OptPrice))
							.replace("tmp3", String.valueOf(OptsetID));
					stmt.executeUpdate(command3);
				}
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}

	/* Display the three tables */
	public void displayDB() {
		Properties props = getProperty();
		try {
			// get connect
			Connection conn = getConnection("AUTOS");
			Statement stmt = conn.createStatement();
			// get sql from the file
			String command1 = props.getProperty("displayAuto");
			ResultSet rs = stmt.executeQuery(command1);
			System.out.println("AutoID  Model    baseprice");
			// extract data from result set
			while (rs.next()) {
				int id = rs.getInt("ID");
				String model = rs.getString("Name");
				String baseprice = rs.getString("baseprice");
				System.out.println(id + "  	" + model + "    " + baseprice);
			}
			System.out.println();
			rs.close();

			String command2 = props.getProperty("displayOPTSET");
			ResultSet rs1 = stmt.executeQuery(command2);
			System.out.println("OptionSetID  Name      AutoID");
			while (rs1.next()) {
				int id = rs1.getInt("ID");
				String name = rs1.getString("Name");
				String AutoID = rs1.getString("AutoID");
				System.out.println(id + "  	  " + name + "    " + AutoID);
			}
			System.out.println();
			rs1.close();

			String command3 = props.getProperty("displayOPTION");
			ResultSet rs2 = stmt.executeQuery(command3);
			System.out.println("OptionID  Name  			Price  	OptionSetID");
			while (rs2.next()) {
				int id = rs2.getInt("ID");
				String name = rs2.getString("Name");
				String price = rs2.getString("price");
				String OptsetID = rs2.getString("OptsetID");
				System.out.println(id + "  " + name + "   		 " + price + "	"
						+ OptsetID);
			}
			System.out.println();
			rs2.close();

		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}

	/*
	 * Delete an Automobile object from AUTOMOBILE Table. Its OptionSet and
	 * Options are also deleted from OPTIONSET Table and OPT Table
	 */
	public void deleteDB(Automobile a1) {
		String model = a1.getModel();

		Properties props = getProperty();
		String deleteAuto = props.getProperty("deleteAuto");
		String command = deleteAuto.replace("tmp1", model);

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection("AUTOS");
			stmt = conn.createStatement();
			stmt.executeUpdate(command);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}

	/* Update the name of an OptionSet to a new name */
	public void updateOptionSetName(String Modelname, String OptionSetname,
			String newName) {
		Properties props = getProperty();
		try {
			Connection conn = getConnection("AUTOS");
			Statement stmt = conn.createStatement();
			// find AutoID
			String command1 = props.getProperty("displayAuto");
			ResultSet rs = stmt.executeQuery(command1);
			int id = 0;
			while (rs.next())
				id = rs.getInt("ID");
			// find the OptionSet using AutoID and old OptionSetname and 
			// set new OptionSetname
			String updateOptset = props.getProperty("updateOptset");
			String command2 = updateOptset.replace("tmp1", newName)
					.replace("tmp2", String.valueOf(id))
					.replace("tmp3", OptionSetname);
			stmt.executeUpdate(command2);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}

	/* Update the price of an Option in OptionSet to a new price */
	public void updateOptionPrice(String Modelname, String Optionname,
			String OptionSetname, float newprice) {
		Properties props = getProperty();
		try {
			Connection conn = getConnection("AUTOS");
			Statement stmt = conn.createStatement();
			// First, find AutoID
			String findAuto = props.getProperty("findAuto");
			String command2 = findAuto.replace("tmp1", Modelname);
			ResultSet rs2 = stmt.executeQuery(command2);
			int AutoID = 0;
			while (rs2.next())
				AutoID = rs2.getInt("ID");
			// Second, find OptionSetID using the AutoID and OptionSetname
			String findOPTSET = props.getProperty("findOPTSET");
			String command1 = findOPTSET
					.replace("tmp1", String.valueOf(AutoID)).replace("tmp2",
							OptionSetname);
			ResultSet rs = stmt.executeQuery(command1);
			int OptionSetID = 0;
			while (rs.next())
				OptionSetID = rs.getInt("ID");
			// Finally, find the Option using the OptionSetID and Optionname and
			// set its new price
			String updateOpt = props.getProperty("updateOpt");
			String command3 = updateOpt
					.replace("tmp1", String.valueOf(newprice))
					.replace("tmp2", String.valueOf(OptionSetID))
					.replace("tmp3", Optionname);
			stmt.executeUpdate(command3);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}

}
