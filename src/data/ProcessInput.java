package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ProcessInput class
 * 
 * 
 * @author Andre Godinez
 *
 */
public class ProcessInput {

	// The list of attributes
	ArrayList<Attribute> attributes;

	// The list of instances
	ArrayList<Instance> instances;

	// the target attribute to classify
	public static Attribute targetAttribute;

	/**
	 * Initialize fields by calling this constructor
	 * 
	 * @param          attributes[] - the array of attributes with the attributeName
	 *                 and the type of values it has
	 * @param fileName - the name of the file
	 * @throws IOException
	 */
	public ProcessInput(String attributes[], String fileName) throws IOException {
		this.attributes = new ArrayList<Attribute>();

		// add all attributes to arraylist of attributes
		for (int i = 0; i < attributes.length; i++) {

			// split the string by all white space i.e. Length real becomes ["Length",
			// "Real", "Target"].
			String[] attribute_value = attributes[i].split(" ");

			// if attribute_value length is not equal to 2 the format is wrong.
			if (attribute_value.length != 3)
				throw new IOException("Invalid attribute format");

			// create a new attribute object and add to the attribute set
			Attribute attr = new Attribute(attribute_value[0], attribute_value[1]);

			if (attribute_value[2].equals("target")) {
				targetAttribute = attr;
			}

			this.attributes.add(attr);
		}

		// add all instances to arraylist of instances
		instances = new ArrayList<Instance>();

		// Initialize scanner to read in file
		Scanner scanIn = new Scanner(new File(fileName));

		while (scanIn.hasNextLine()) {
			String line = scanIn.nextLine();

			String[] lineArray = line.split(",");
			Instance instance = new Instance();

			for (int i = 0; i < lineArray.length; i++) {
				instance.addAttribute(this.attributes.get(i).getName(), lineArray[i]);
			}

			// test
			System.out.println(instance);

			this.instances.add(instance);
		}

		scanIn.close();
	}

	public ArrayList<Attribute> getAttributes() {
		return this.attributes;
	}

	public ArrayList<Instance> getInstances() {
		return this.instances;
	}

	public Attribute getTargetAttribute() {
		return targetAttribute;
	}

	public static void main(String args[]) throws IOException {
		// test
		String fileName = "owls.csv";
		String[] attributes = { "body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
				"type [Barn_Owl,Snowy_Owl,Long-Eared_Owl] target" };
		ProcessInput pi = new ProcessInput(attributes, fileName);
//		System.out.println(pi.getAttributes());
//		System.out.println(pi.getInstances());
//		System.out.println(pi.getTargetAttribute());
	}

}
