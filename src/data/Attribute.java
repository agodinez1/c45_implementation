package data;

import java.io.IOException;
import java.util.ArrayList;

public class Attribute {

	// name of attribute
	private String name;

	// type of attribute - if continuous or discrete
	private String type;

	// if it is discrete the labels are stored here otherwise "real" is stored
	private ArrayList<String> labels;

	public Attribute(String name, String labels) throws IOException {
		this.name = name;
		this.type = labels.equals("real") ? "continuous" : "discrete";
		this.labels = new ArrayList<String>();

		if (this.type.equals("discrete")) {
			this.labels = mapLabels(labels);
		} else {
			this.labels.add("real");
		}
	}

	private ArrayList<String> mapLabels(String temp) throws IOException {
		if (temp == null || temp.length() < 2)
			throw new IOException("Invalid label format");

		// since the labels come in [label1,label2,label3] we remove the [ ]
		temp = temp.substring(1, temp.length() - 1);

		String[] labelsArray = temp.split(",");

		ArrayList<String> labels = new ArrayList<String>();
		for (String label : labelsArray) {
			labels.add(label);
		}
		return labels;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public String toString() {
		return "Attribute name: " + this.name + "; type: " + this.type + "; labels : " + this.labels;
	}

	public static void main(String[] args) throws IOException {
		String name = "body-length";
		String labels = "real";
		Attribute test = new Attribute(name, labels);
		System.out.println(test);
		name = "type";
		labels = "[Barn_Owl,Snowy_Owl,Long-Eared_Owl]";
		Attribute test2 = new Attribute(name, labels);
		System.out.println(test2);
	}
}
