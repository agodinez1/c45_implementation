package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import data.Attribute;
import data.Instance;

/**
 * Entropy class to calculate entropy for a target attribute
 * 
 * @author Andre Godinez
 *
 */
public class Entropy {

	public static double calculateDiscrete(Attribute target, ArrayList<Instance> instances) throws IOException {
		// get target name
		String targetName = target.getName();

		// get target labels since the data is discrete
		ArrayList<String> targetLabels = target.getLabels();

		
		HashMap<String, Integer> labelCount = calculateLabelCount(targetLabels, instances);

		return null;
	}

	private static HashMap<String, Integer> calculateLabelCount(ArrayList<String> targetLabels,
			ArrayList<Instance> instances) {
		
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		
		// initialize each label count to be 0 for each label
		for (String label : targetLabels) {
			labelCount.put(label, 0);
		}
		
		HashMap<String, String> attributeValuePair;
		// for each instance 
		for (Instance instance : instances) {
			attributeValuePair = instance.getAttributeValuePairs();
			
			// 
		}
		return null;
	}

}
