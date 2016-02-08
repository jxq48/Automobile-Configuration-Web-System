package adapter;


import java.util.Properties;
import java.util.Set;

import database.UpdateTable;
import exception.AutoException;
import exception.Fix1to5;
import scale.EditOptions;
import util.FileIO;
import util.ReadData;
import model.*;

public abstract class ProxyAutomobile {
	private static Automobile a1;
	public static boolean flag = false;
	public static Fleet map = new Fleet(); 
	
	public Fleet getMap() {
		return map;
	}

	public void updateOptionSetName(String Modelname, String OptionSetname,
			String newName) {
		a1 = map.read(Modelname);
		if (a1 != null) {
			a1.updateOpsetName(OptionSetname, newName);
			AutoDB db =  new UpdateTable();
			db.updateOptionSetName(Modelname, OptionSetname, newName);
		}
	}

	public void updateOptionPrice(String Modelname, String Optionname,
			String OptionSetname, float newprice) {
		a1 = map.read(Modelname);
		if (a1 != null) {
			a1.updateOptionPrice(OptionSetname, Optionname, newprice);
			AutoDB db =  new UpdateTable();
			db.updateOptionPrice(Modelname, Optionname, OptionSetname, newprice);
		}

	}
	/*
	 * Read data from different type of files and build Automobile object. If
	 * fileType is 0, input file is original file. If fileType is 1, input file
	 * is Properties file.
	 */
	public void buildAuto(String filename, int fileType) {
		if (fileType == 0) {
			ReadData readdata = new ReadData(filename);
			/*
			 * Flag is public static variable. It will be set to true if an
			 * exception is handled.
			 */
			do {
				try {
					a1 = readdata.buildAutoObject();
				} catch (AutoException e) {
					int errNo = e.getErrorno();
					/*
					 * IOException is raised and its errNo = 1. A new filename
					 * will be set from user input.
					 */
					if (errNo == 1)
						readdata.setFilename(e.fix(e.getErrorno(),
								e.getErrormsg()));
					/*
					 * Rest of exception will terminate the program. Their
					 * Errorno and Errormsg will be output.
					 */
					else {
						e.fix(e.getErrorno(), e.getErrormsg());
					}
				}
			} while (flag == false);
			if (a1 != null) {
				map.create(a1.getModel(), a1);
				insertDB(a1);			
			}
		} 
		else if (fileType == 1) {
			FileIO fileIO= new FileIO();
			a1 = fileIO.readAutoFromPropertyFile(filename);
			if (a1 != null) {
				map.create(a1.getModel(), a1);
				insertDB(a1);	
			}
		}
	}

	public void printAuto(String Modelname) {
		a1 = map.read(Modelname);
		if (a1 != null)
			a1.printAuto();
		else
			System.out.println("Can't find"+Modelname);
	}
	
	public String fix(int errorno, String errormsg) {
		Fix1to5 f1 = new Fix1to5();
		String result = null;
		switch(errorno) {
		case 2 : f1.fix_wrongBaseprice(errorno, errormsg);break;
		case 1 : result = f1.fix_missFilename(errorno, errormsg);break;
		case 3 : f1.fix_missModelName(errorno, errormsg);break;
		case 4 : f1.fix_missOptionName(errorno, errormsg);break;
		case 5 : f1.fix_missOptionSet(errorno, errormsg);break;
		}
		return result;
	}

	//Followings are Unit 3:
	//Start T1 and T2
	public void threadSetOptionChoice(String id, int ThreadNum) {
		EditOptions t = new EditOptions(id,ThreadNum);
		t.start();
	}
	//Start T3 and T4
	public void threadUpdateOptionPrice(String id, int ThreadNum) {
		EditOptions t = new EditOptions(id,ThreadNum);
		t.start();
	}
	//Start T5 and T6
	public void threadDeleteOption(String id, int ThreadNum) {
		EditOptions t = new EditOptions(id,ThreadNum);
		t.start();
	}
	
	/*Accept properties object, create an Automobile and put it to the LinkedHashMap*/
	public void buildAutoFromProperty(Properties props) {
		FileIO fileIO= new FileIO();
		a1 = fileIO.readAutoFromProperty(props);
		if (a1 != null)
			map.create(a1.getModel(), a1);
	}
	
	/*Provide a list of available models */
	public Set<String> ProvideAutoList() {
		BuildAuto b1 = new BuildAuto();
		Set<String> list = b1.getMap().getKeys();
		return list;
	}
	
	/*Provide an Automobile object from LinkedHashMap given by its model name*/
	public Automobile ProvideAuto(String model) {
		BuildAuto e1 = new BuildAuto();
		Automobile automobile = e1.getMap().read(model);
		return automobile;
	}
	
	/*Override the following methods of AutoDB interface*/
	public void insertDB(Automobile a1) {
		AutoDB db =  new UpdateTable();
		db.insertDB(a1);
	}

	public void displayDB() {
		AutoDB db =  new UpdateTable();
		db.displayDB();
	}
	
	public void deleteDB(String model) {
		Automobile auto = getMap().read(model);
		deleteDB(auto);
	}
	
	public void deleteDB(Automobile a1) {
		AutoDB db =  new UpdateTable();
		db.deleteDB(a1);
	}
}
