package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import adapter.BuildAuto;
import adapter.CreateAuto;

public class CarModelOptionsIO implements CreateAuto, SocketClientConstants {

	@Override
	/*
	 * Read data from different type of files and build Automobile object. If
	 * fileType is 0, input file is original file. If fileType is 1, input file
	 * is Properties file.
	 */
	public void buildAuto(String filename, int fileType) {
		CreateAuto createauto = new BuildAuto();
		createauto.buildAuto(filename, fileType);
	}

	@Override
	/*Print Automobile given its model name*/
	public void printAuto(String Modelname) {
		CreateAuto createauto = new BuildAuto();
		createauto.printAuto(Modelname);
	}

	/*Read data from the properties file, create properties object using the load method.*/
	public Properties UploadPropertiesFile(String filename) {
		Properties props = new Properties();
		FileInputStream in = null;

		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			return null;
		}
		try {
			props.load(in);
		} catch (IOException e) {
			return null;
		}

		return props;
	}
	
	/*Print out 'create successfully' when receive the response from server*/
	public void CheckResponse(int response) {
		if (response == CREATE_AUTO_SUCC)
			System.out
					.println("Server create the Car Model Object successfully"
							+ "\n");
	}

}
