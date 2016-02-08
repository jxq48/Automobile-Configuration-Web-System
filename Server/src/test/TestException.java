package test;

import adapter.BuildAuto;

public class TestException {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		BuildAuto auto1 = new BuildAuto();
		/*Test case 1: wrong file name, which will be fixed*/
		auto1.buildAuto("wrongFilename",0);
		
		/*Test case 2: wrong base price*/
//		auto1.buildAuto("wrongBaseprice.txt",0);
		
		/*Test case 3:miss model name*/
//		auto1.buildAuto("missModelName.txt",0);
		
		/*Test case 4: miss option name*/
//		auto1.buildAuto("missOptionName.txt",0);
		
		/*Test case 5: miss OptionSet*/
//		auto1.buildAuto("missOptionSet.txt",0);
		auto1.map.printMap();
	}
}
