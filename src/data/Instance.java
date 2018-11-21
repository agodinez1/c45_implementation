package data;

import java.util.HashMap;

public class Instance {

	private static int indexCount = 0;
	
	private int index;
	
	private HashMap<String, String> attributeValuePairs;
	
	public Instance() {
		this.index = indexCount;
		attributeValuePairs = new HashMap<String, String>();
		
		indexCount++;
	}
	
	public void addAttribute(String name, String value) {
		attributeValuePairs.put(name, value);
	}
	
	public int getInstanceIndex() {
		return this.index;
	}

	public HashMap<String,String> getAttributeValuePairs() {
		return this.attributeValuePairs;
	}
	
	public String toString() {
		return "[" + index + "]" + this.attributeValuePairs;
	}

	public static void main(String args[]) {
		Instance test = new Instance();
		test.addAttribute("body-length", "1");
		test.addAttribute("body-width", "2");
		System.out.println(test);
		Instance test2 = new Instance();
		test2.addAttribute("body-length", "1");
		test2.addAttribute("body-width", "2");
		System.out.println(test2);
	}
	
}

