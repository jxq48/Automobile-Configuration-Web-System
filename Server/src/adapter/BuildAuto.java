package adapter;

import exception.FixAuto;
import scale.Editthread;
import server.AutoServer;

public class BuildAuto
	extends ProxyAutomobile implements CreateAuto, UpdateAuto, FixAuto, Editthread, AutoServer, AutoDB {
	
}
