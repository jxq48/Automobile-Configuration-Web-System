package server;

import java.util.Properties;
import java.util.Set;

import model.Automobile;


public interface AutoServer {
	public void buildAutoFromProperty(Properties props);
	public Set<String> ProvideAutoList();
	public Automobile ProvideAuto(String model);
}
