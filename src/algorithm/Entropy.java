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

		// number of times each label value shows up in the instances
		HashMap<String, Integer> labelCount = calculateLabelCount(targetName, targetLabels, instances);

		// Entropy(S) = ∑ – p(I) . log2p(I)

		int totalSize = instances.size();
		double entropy = 0.0;

		for ( int value : labelCount.values() ) {
			//	– p(I) . log2p(I)
			double p_i = (double) value/totalSize;
			double calc = (-1 * p_i ) * (Math.log(p_i)/Math.log(2)) ;
			entropy+=calc;
		}

		return entropy;
	}

	private static HashMap<String, Integer> calculateLabelCount(String targetName, ArrayList<String> targetLabels,
			ArrayList<Instance> instances) throws IOException {
		
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		
		// initialize each label count to be 0 for each label
		for (String label : targetLabels) {
			labelCount.put(label, 0);
		}

		// temp variable to hold attributevalue pair per instance
		HashMap<String, String> attributeValuePairs;

		for (Instance instance : instances) {
			String instanceTargetValue = instance.getAttributeValuePairs().get(targetName);

			// if current instance does not have a valid target attribute throw error
			if (!labelCount.containsKey(instanceTargetValue)) throw new IOException("Error: Instance does not have a target value");

			// else we increment 1 to the existing label
			labelCount.put(instanceTargetValue, labelCount.get(instanceTargetValue) + 1);
		}

		return labelCount;
	}

	public static void main (String args[]) {

	}

}
