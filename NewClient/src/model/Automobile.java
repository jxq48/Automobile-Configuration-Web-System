package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.OptionSet.Option;

/*Automotive class has name, base price and a set of properties of a model.*/
/*All methods are synchronized*/
@SuppressWarnings("serial")
public class Automobile implements Serializable {
	private String make;
	private String model;
	private ArrayList<Option> choice;
	
	private float baseprice;
	private ArrayList<OptionSet> opset;
	
	public Automobile() {
	}
	
	public Automobile(String make, String model, float baseprice) {
		opset = new ArrayList<OptionSet>();
		this.baseprice = baseprice;
		this.make = make;
		this.model = model;
		choice = new ArrayList<Option>();
	}
	
	public synchronized float getBaseprice() {
		return baseprice;
	}
	
	public synchronized OptionSet getOpset(int index) {
		if (index < opset.size())
			return opset.get(index);
		else
			return null;
	}
	
	public synchronized String getMake() {
		return make;
	}
	
	public synchronized String getModel() {
		return model;
	}
	
	public synchronized String getOptionChoice(String setName) {
		OptionSet optionset = findOpset(setName);
		if (optionset != null) {
			Option option = optionset.getOptionChoice();
			if (option != null)
				return option.getName();
		}
		return null;
	}
	
	public synchronized int getOptionChoicePrice(String setName) {
		OptionSet optionset = findOpset(setName);
		if (optionset != null) {
			Option option = optionset.getOptionChoice();
			if (option != null)
				return (int)(option.getPrice());
		}
		return -1;
	}
	
	public synchronized void setBaseprice(float baseprice) {
		this.baseprice = baseprice;
	}
	
	public synchronized void setOpset(String OptionSetName) {
		OptionSet optionset =  new OptionSet(OptionSetName);
		opset.add(optionset);
	}

	public synchronized void setOption(OptionSet optionset, String OptionName, float price) {
		optionset.setOpt(OptionName, price);
	}
	
	public synchronized void setMake(String make) {
		this.make = make;
	}
	
	public synchronized void setModel(String model) {
		this.model = model;
	}
	
	public synchronized void setOptionChoice(String setName, String optionName) {
		OptionSet optionset = findOpset(setName);
		if (optionset != null) {
			Option option = optionset.findOpt(optionName);
			if (option != null)
				choice.add(option);
			optionset.setOptionChoice(optionName);
		}
	}
	
	/*Find OptionSet by name*/
	public synchronized OptionSet findOpset(String name) {
		for (OptionSet optionset : opset) {
			if (optionset.getName().equals(name))
				return optionset;
		}
		return null;
	}
	
	
	/*Find Option by OptionSet name in the context of OptionSet*/
	public synchronized OptionSet.Option findOption(String OptionName, OptionSet optionset) {
		Option option = optionset.findOpt(OptionName);
		if(option != null)
			return option;
		else
			return null;
	}
	
	public synchronized OptionSet.Option findOptionWithIndex(int index, OptionSet optionset) {
		Option option = optionset.findOptWithIndex(index);
		if(option != null)
			return option;
		else
			return null;
	}
	
	/*Find the OptionSet by its name and set it to null*/
	public synchronized void deleteOpsetByName(String name) {
		OptionSet optionset = findOpset(name);
		if (optionset != null)
			opset.remove(optionset);
	}
	
	
	/*Delete option from giver option set name and option name*/
	public synchronized void deleteOptionByName(String OptionSetName, String OptionName) {
		OptionSet optionset = findOpset(OptionSetName);
		if (optionset != null)
			optionset.deleteOptByName(OptionName);
	}
	
	
	/*Update the option set with its name*/
	public synchronized void updateOpsetName(String OptionSetName, String newOptionSetName) {
		OptionSet optionset = findOpset(OptionSetName);
		if (optionset != null)
			optionset.setName(newOptionSetName);
	}
	
	/*Update price and name of the option*/
	public synchronized void updateOption(String OptionSetName, String OptionName, String newOptionName, float newPrice) {
		OptionSet optionset = findOpset(OptionSetName);
		if (optionset != null)
			optionset.updateOpt(OptionName, newOptionName, newPrice);
	}
	
	/*Update name of the option*/
	public synchronized void updateOptionName(String OptionSetName, String OptionName, String newOptionName) {
		OptionSet optionset = findOpset(OptionSetName);
		if (optionset != null)
			optionset.updateOptName(OptionName, newOptionName);
	}
	
	/*Update price of the option*/
	public synchronized void updateOptionPrice(String OptionSetName, String OptionName, float newPrice) {
		OptionSet optionset = findOpset(OptionSetName);
		if (optionset != null)
			optionset.updateOptPrice(OptionName, newPrice);
	}
	
	
	public synchronized int getTotalPrice()  {
		int TotalPrice = (int) baseprice;
		for (Option option: choice) 
			TotalPrice += option.getPrice();
		return TotalPrice;
	}
	
	public synchronized void printAuto() {
		StringBuffer result = new StringBuffer("Make: ");
		result.append(getMake());
		result.append(",Model: ");
		result.append(getModel());
		result.append(",baseprice: ");
		result.append(getBaseprice());
		System.out.println(result);
		for (OptionSet optionset: opset) {
			if (optionset != null)
				optionset.printOpset();
		}
	}
	
	
	public synchronized void printChoice() {	
		StringBuffer result = new StringBuffer("Make: ");
		result.append(getMake());
		result.append(",Model: ");
		result.append(getModel());
		System.out.println(result);
		for (Option option: choice) {
			if (option != null)
				printOption(option);
		}
		System.out.println("Total price is: ");
		System.out.println(getTotalPrice()+"\n");
	}
	
	public synchronized void printOptionSet(OptionSet optionset) {
		optionset.printOpset();
	}
	
	public synchronized void printOption(Option option) {
		option.printOpt();
	}
	
	public synchronized String getOpsetName(OptionSet opset) {
		return opset.getName();
	} 
	
	public synchronized int getOpsetSize() {
		return opset.size();
	}
	
	public synchronized int getOptSize(OptionSet opset) {
		return opset.getOptSize();
	}
	
	public synchronized String getOptName(int index, OptionSet opset) {
		Option option = findOptionWithIndex(index, opset);
		if (option != null)
			return option.getName();
		else
			return null;
	}
	
	public synchronized float getOptPrice(OptionSet opset, String OptionName) {
		Option option = findOption(OptionName, opset);
		if (option != null)
			return option.getPrice();
		return -1;
	}
	
}

