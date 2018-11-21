package data;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Attribute class to represent an attribute
 * 
 * @author Andre Godinez
 *
 */
public class Attribute {

	// name of attribute
	private String name;

	// type of attribute - if continuous or discrete
	private String type;

	// if it is discrete the features are stored here otherwise "real" is stored
	private ArrayList<String> features;

	/**
	 * The Attribute constructor
	 * 
	 * @param name   - the name of the attribute
	 * @param features - the features of the attribute
	 * @throws IOException
	 */
	public Attribute(String name, String features) throws IOException {
		this.name = name;
		this.type = features.equals("real") ? "continuous" : "discrete";
		this.features = new ArrayList<String>();

		if (this.type.equals("discrete")) {
			this.features = mapFeatures(features);
		} else {
			this.features.add("real");
		}
	}

	/**
	 * Function to map the String features to an arraylist of features
	 * 
	 * @param temp - the string of features e.g. "[Barn_Owl,Snowy_Owl,Long-Eared_Owl]"
	 * @return ArrayList of features
	 * @throws IOException
	 */
	private ArrayList<String> mapFeatures(String temp) throws IOException {
		if (temp == null || temp.length() < 2)
			throw new IOException("Invalid label format");

		// since the features come in [label1,label2,label3] we remove the [ ]
		temp = temp.substring(1, temp.length() - 1);

		String[] featuresArray = temp.split(",");

		ArrayList<String> features = new ArrayList<String>();
		for (String label : featuresArray) {
			features.add(label);
		}
		return features;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public ArrayList<String> getFeatures() {
		return this.features;
	}
	
	public String toString() {
		return "Attribute name: " + this.name + "; type: " + this.type + "; features : " + this.features;
	}

	public static void main(String[] args) throws IOException {
		String name = "body-length";
		String features = "real";
		Attribute test = new Attribute(name, features);
		System.out.println(test);
		name = "type";
		features = "[Barn_Owl,Snowy_Owl,Long-Eared_Owl]";
		Attribute test2 = new Attribute(name, features);
		System.out.println(test2);
	}
}
