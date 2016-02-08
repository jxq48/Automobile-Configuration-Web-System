package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import adapter.ProxyAutomobile;
import exception.AutoException;
import exception.MissModelName;
import exception.MissOptionName;
import exception.MissOptionSet;
import exception.WrongBaseprice;
import model.Automobile;
/*Read the data from input file*/
public class ReadData {
	private String filename;

	public ReadData(String filename) {
		this.filename = filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@SuppressWarnings({ "resource" })
	/*
	 * Read data from input file and build an Automotive object. It set values
	 * of five OptionSets of the automotive and set values of Options of each
	 * OptionSets. There are five exceptions may happening when build Automobile
	 * object. The exception 1 -- IOException with wrong file name will be fixed
	 * directly. For the other four exceptions, just print out their error
	 * number and error message.
	 */
	public Automobile buildAutoObject()
			throws AutoException {
		Automobile automobile;
		try {
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
			String line = buff.readLine();
			StringTokenizer model = new StringTokenizer(line, ",");
			String make = model.nextToken();
			String modelname = model.nextToken();
			if (modelname.equals(" "))
				throw new MissModelName(3, "missModelName");

			int baseprice = Integer.parseInt(model.nextToken());

			if (baseprice < 0)
				throw new WrongBaseprice(2, "wrongBaseprice");
			int OptionSetsize = Integer.parseInt(model.nextToken());

			automobile = new Automobile(make, modelname, baseprice);
			int indexofOptionSet = 0;
			while ((line = buff.readLine()) != null) {
				StringTokenizer result = new StringTokenizer(line, ",");
				String OpsetName = result.nextToken();

				/* Build the OptionSet */
				automobile.setOpset(OpsetName);
				/* Build the Option inside the OptionSet */
				while (result.hasMoreTokens()) {
					String OptionName = result.nextToken();
					if (OptionName.equals(" "))
						throw new MissOptionName(4, "missOptionName");
					automobile.setOption(automobile.findOpset(OpsetName),
							OptionName, Float.parseFloat(result.nextToken()));
				}
				indexofOptionSet++;
			}
			if (indexofOptionSet != OptionSetsize)
				throw new MissOptionSet(5, "missOptionSet");
			ProxyAutomobile.flag = true;
		} catch (IOException e) {
			throw new AutoException(1, "Wrong file name!");
		} catch (WrongBaseprice e) {
			throw new AutoException(e.getErrorno(), e.getErrormsg());
		} catch (MissModelName e) {
			throw new AutoException(e.getErrorno(), e.getErrormsg());
		} catch (MissOptionSet e) {
			throw new AutoException(e.getErrorno(), e.getErrormsg());
		} catch (MissOptionName e) {
			throw new AutoException(e.getErrorno(), e.getErrormsg());
		}
		return automobile;
	}
}