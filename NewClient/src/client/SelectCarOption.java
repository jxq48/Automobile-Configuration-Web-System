package client;

import java.util.Scanner;

import model.Automobile;


public class SelectCarOption {

	/*Print out a list of available models*/
	public void PrintAutoList(String list) {
			System.out.println("Following are available models:");
			System.out.println(list);
	}

	/*Allow user to enter respective options of selected Automobile and set user's choices*/
	public void ConfigureAuto(Scanner strInput, Automobile automobile) {
		automobile.printAuto();
		while (true) {
			System.out
					.println("Please input a OptionSet name. Input 'return' to print Option choices");
			String setName = strInput.nextLine();
			if (setName.equals("return"))
				break;
			System.out
					.println("Please input the name of Option you want to select");
			String optionName = strInput.nextLine();
			automobile.setOptionChoice(setName, optionName);
		}		
	}
	/*Print out user selected options*/
	public void PrintSelectedOptions(Automobile automobile) {
		automobile.printChoice();
	}


}
