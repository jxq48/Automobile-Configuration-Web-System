package model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;


@SuppressWarnings("serial")
public class Fleet implements Serializable{
	private static LinkedHashMap<String, Automobile> map = new LinkedHashMap<String, Automobile>();
	
	public Automobile read(String name) {
		if (map.containsKey(name))
			return map.get(name);
		else
			return null;
	}
	
	public void create(String name, Automobile automobile) {
		map.put(name, automobile);
	}
	
	public void update(String name, Automobile automobile) {
		if (map.containsKey(name))
			map.replace(name,automobile);
	}
	
	public void delete(String name) {
		if (map.containsKey(name))
			map.remove(name);
	}
	
	public void printMap() {
		Set<String> modelname = map.keySet();
		Iterator<String> imodelname = modelname.iterator();
		while (imodelname.hasNext()) {
			Automobile automobile = read(imodelname.next());
			automobile.printAuto();
		}
	}
	
	public Set<String> getKeys() {
		Set<String> k = map.keySet();
		return k;
	}
}

