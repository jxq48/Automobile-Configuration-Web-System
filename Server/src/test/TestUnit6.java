package test;

import database.InitialDB;
import adapter.BuildAuto;

public class TestUnit6 {
	public static void main(String arg[]) {
		BuildAuto buildauto = new BuildAuto();
		/*Initialize Database*/
		InitialDB db = new InitialDB();	
		db.run();
		/*Display Database after adding two automobiles*/
		buildauto.buildAuto("HondaCivic.txt", 0);
		buildauto.buildAuto("ToyotaCorolla.txt", 0);
		System.out.println("Display Database after adding two automobiles:");
		buildauto.displayDB();
		
		/*Update Civic's OptionSet name Color to ColorOfCivic*/
		buildauto.updateOptionSetName("Civic", "Color", "ColorOfCivic");
		/*Update Civic's Brakes/Traction Control's Option ABS's price from 400 to 800*/
		buildauto.updateOptionPrice("Civic","ABS","Brakes/Traction Control",800);
		System.out.println("Display Database after update Civic's OptoinSet Name from Color to ColorOfCivic and Option ABS's price from 400 to 800:");
		buildauto.displayDB();
		
		/*Delete an automobile from database*/
		buildauto.deleteDB("Civic");
		System.out.println("Civic has been deleted: ");
		buildauto.displayDB();
	}
}
