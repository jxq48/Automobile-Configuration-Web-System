package server;

import java.util.Properties;
import java.util.Set;

import model.Automobile;
import adapter.BuildAuto;

public class BuildCarModelOptions implements AutoServer {
	
	/*Accept properties object, create an Automobile and put it to the LinkedHashMap*/
	public void buildAutoFromProperty(Properties props) {
		AutoServer auto = new BuildAuto();
		auto.buildAutoFromProperty(props);		
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
	


}
