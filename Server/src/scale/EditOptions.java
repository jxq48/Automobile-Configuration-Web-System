package scale;

import model.Automobile;
import adapter.BuildAuto;

public class EditOptions extends Thread {
	private int ThreadNum;

	public EditOptions(String id, int ThreadNum) {
		/* set the thread ID. */
		super(id);
		/* set the thread number. */
		this.ThreadNum = ThreadNum;
	}

	public void SetOptionChoice(String Modelname, String OptionSetName,
			String OptionName) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null)
			a1.setOptionChoice(OptionSetName, OptionName);
	}

	public String GetOptionChoice(String Modelname, String OptionSetName) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null)
			return a1.getOptionChoice(OptionSetName);
		else
			return null;
	}

	public void UpdateOptionPrice(String Modelname, String OptionSetName,
			String Optionname, float newprice) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null) {
			a1.updateOptionPrice(OptionSetName, Optionname, newprice);
		}
	}
	
	public void GetOption(String Modelname, String OptionSetName, String OptionName) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null)
			a1.printOption(a1.findOption(OptionName, a1.findOpset(OptionSetName)));
	}
	
	public void DeleteOption(String Modelname, String OptionSetName, String OptionName) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null)
			a1.deleteOptionByName(OptionSetName, OptionName);
	}
	
	public void GetOptionSet(String Modelname, String OptionSetName) {
		BuildAuto e1 = new BuildAuto();
		Automobile a1 = e1.getMap().read(Modelname);
		if (a1 != null)
			a1.printOptionSet(a1.findOpset(OptionSetName));
	}
	
	public void run() {
		synchronized(System.out){
		switch(ThreadNum) {
		case 1: runT1();break;
		case 2: runT2();break;
		case 3: runT3();break;
		case 4: runT4();break;
		case 5: runT5();break;
		case 6: runT6();break;
		}
		}
	}

	public void runT1() {
		System.out.println("this is t1:");
		SetOptionChoice("Focus Wagon ZTW", "Color",
				"Fort Knox Gold Clearcoat Metallic");
		SetOptionChoice("Focus Wagon ZTW", "Transmission",
				"automatic");
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		System.out.println("Print Option choices:");
		System.out.println(GetOptionChoice("Focus Wagon ZTW", "Color"));
		System.out.println(GetOptionChoice("Focus Wagon ZTW", "Transmission"));
		System.out.println();
	}
	
	public void runT2() {
		System.out.println("this is t2:");
		SetOptionChoice("Focus Wagon ZTW", "Color",
				"Cloud 9 White Clearcoat");
		SetOptionChoice("Focus Wagon ZTW", "Transmission",
				"manual");
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		System.out.println("Print Option choices:");
		System.out.println(GetOptionChoice("Focus Wagon ZTW", "Color"));
		System.out.println(GetOptionChoice("Focus Wagon ZTW", "Transmission"));
		System.out.println();
	}
	
	public void runT3() {
		System.out.println("this is t3:");
		System.out.println("Before updating Option name and price:");
		GetOption("Civic", "Color","Gold Clearcoat Metallic");
		GetOption("Civic", "Brakes/Traction Control", "ABS with Advance Trac");
		
		UpdateOptionPrice("Civic", "Color", "Gold Clearcoat Metallic",50);
		UpdateOptionPrice("Civic", "Brakes/Traction Control", "ABS with Advance Trac",1025);
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		System.out.println("After updating Option name and price:");
		GetOption("Civic", "Color", "Gold Clearcoat Metallic");
		GetOption("Civic", "Brakes/Traction Control", "ABS with Advance Trac");
		System.out.println();
	}
	
	public void runT4() {
		System.out.println("this is t4:");
		System.out.println("Before updating Option name and price:");
		GetOption("Civic", "Color", "Gold Clearcoat Metallic");
		GetOption("Civic", "Brakes/Traction Control", "ABS with Advance Trac");		
		
		UpdateOptionPrice("Civic", "Color", "Gold Clearcoat Metallic",20);
		UpdateOptionPrice("Civic", "Brakes/Traction Control", "ABS with Advance Trac", 800);
				
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		
		System.out.println("After updating Option name and price:");
		GetOption("Civic", "Color", "Gold Clearcoat Metallic");
		GetOption("Civic", "Brakes/Traction Control", "ABS with Advance Trac");
		System.out.println();
	}
	
	public void runT5() {
		System.out.println("this is t5:");
		System.out.println("Start deleting Fort Knox Gold Clearcoat Metallic:");
		DeleteOption("Focus Wagon ZTW", "Color","Fort Knox Gold Clearcoat Metallic");
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		GetOptionSet("Focus Wagon ZTW", "Color");
		System.out.println();
	}
	
	public void runT6() {
		System.out.println("this is t6:");
		System.out.println("Start deleting Liquid Grey Clearcoat Metallic:");
		DeleteOption("Focus Wagon ZTW", "Color","Liquid Grey Clearcoat Metallic");
		try {
			Thread.sleep(100);
		}catch(Exception e) {};
		GetOptionSet("Focus Wagon ZTW", "Color");
		System.out.println();
	}
	
}
