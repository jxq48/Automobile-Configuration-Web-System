package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import model.Automobile;

public class FileIO {
	/*Serializes automotive object to the file named auto.ser*/
	public void serializeAuto(Automobile automotive) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("auto.ser"));
			out.writeObject(automotive);
			out.close();
		}catch (Exception e) {
			System.out.print("Error: "+e);
			System.exit(1);;
		}
	}
	/*DeserializeAuto method de-serialize object from the file*/
	public Automobile DeserializeAuto(String filename) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			Automobile automotive = (Automobile)in.readObject();
			in.close();
			return automotive;
		}catch (Exception e) {
			System.out.print("Error: "+e);
			System.exit(1);
		}
		return null;
	}
	
	/*Accept properties object and create an Automobile*/
	public Automobile readAutoFromProperty(Properties props) {
		Automobile automobile = null;
		String CarMake = props.getProperty("CarMake");
		if (!CarMake.equals(null)) {
			String CarModel = props.getProperty("CarModel");
			String BasePrice = props.getProperty("BasePrice");
			automobile = new Automobile(CarMake, CarModel, Float.parseFloat(BasePrice));
			String OptionSet = null;
			
			int i = 1;
			while (true) {
				OptionSet = props.getProperty("Option"+i);
				if (OptionSet == null)
					break;
				automobile.setOpset(OptionSet);			
				String Option = null;
				String OptionPrice = null;
				char j = 'a';
				while (true) {
					Option = props.getProperty("OptionValue"+i+j);
					if (Option == null)
						break;
					OptionPrice = props.getProperty("OptionPrice"+i+j); 
					automobile.setOption(automobile.findOpset(OptionSet), Option, Float.parseFloat(OptionPrice));
					j ++;
				} 
				i ++;
			} 
		}
		return automobile;
	}
	
	/*Create an Automobile from property file*/
	public Automobile readAutoFromPropertyFile(String filename) {
		Properties props = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Automobile automobile = readAutoFromProperty(props);
		return automobile;
	}
}
