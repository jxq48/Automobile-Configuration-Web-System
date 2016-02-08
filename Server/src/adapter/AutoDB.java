package adapter;

import model.Automobile;

public interface AutoDB {
	public void insertDB(Automobile a1);

	public void displayDB();

	public void deleteDB(Automobile a1);

	public void updateOptionSetName(String Modelname, String OptionSetname,
			String newName);

	public void updateOptionPrice(String Modelname, String Optionname,
			String OptionSetname, float newprice);
}
