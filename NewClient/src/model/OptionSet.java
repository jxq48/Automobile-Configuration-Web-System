package model;

import java.io.Serializable;
import java.util.ArrayList;

/*OptionSet class has name of a property and set of options of the property*/
@SuppressWarnings("serial")
public class OptionSet implements Serializable{
	private ArrayList<Option> opt;
	private String name;
	private String choice;
	
	protected OptionSet(String n) {
		opt = new ArrayList<Option>();
		name = n;
	}
		
	protected String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}

	protected void setOpt(String name, float price) {
		Option option = new Option(name, price);
		opt.add(option); 
	}
	
	protected void setOptionChoice(String optionName) {
		choice = optionName;
	}
	
	protected Option getOptionChoice() {
		Option option = findOpt(choice);
		if (option != null)
			return option;
		else
			return null;
	}
	
	/*Find option by name. Return the found option. If not exist return null.*/
	protected Option findOpt(String name) {
		for (Option option: opt) {
			if(option.getName().equals(name))
				return option;
		}
		return null;
	}
	
	protected Option findOptWithIndex(int index) {
		if (opt.get(index) != null)
			return opt.get(index);
		else
			return null;
	}

	/*Find option by name and set it to null.*/
	protected void deleteOptByName(String name) {
		Option option = findOpt(name);
		if (option != null)
			opt.remove(option);
	} 
	
	
	/*Find the option to be updated, then update its properties*/
	protected void updateOpt(String name, String newName, float newPrice) {
		Option option = findOpt(name);
		option.setPrice(newPrice);
		option.setName(newName);
	}
	
	protected void updateOptName(String name, String newName) {
		Option option = findOpt(name);
		option.setName(newName);
	}
	
	protected void updateOptPrice(String name, float newPrice) {
		Option option = findOpt(name);
		option.setPrice(newPrice);
	}
		
	protected void printOpset() {
		StringBuffer result = new StringBuffer("OptionSet name: ");
		result.append(getName());
		System.out.println(result);
		for (Option option: opt) {
			if (option != null)
				option.printOpt();
		}
		System.out.println();
	}
	
	protected int getOptSize() {
		return opt.size();
	}

	/*Inner class of OptionSet. Represent options within OptionSet*/
	class Option implements Serializable{
		private String name;
		private float price;
		
		protected Option(String name, float price) {
			this.name = name;
			this.price = price;
		}
		
		protected String getName() {
			return name;
		}
		
		protected float getPrice() {
			return price;
		}
		
		protected void setName(String name) {
			this.name = name;
		}
		
		protected void setPrice(float price) {
			this.price = price;
		}
		
		protected void printOpt() {
			StringBuffer result = new StringBuffer("Option name: ");
			result.append(getName());
			result.append(",option price: ");
			result.append(getPrice());
			System.out.println(result);
		}
		
	}
}
