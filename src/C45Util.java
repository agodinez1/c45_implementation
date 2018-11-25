import java.util.*;
import java.util.stream.Collectors;

public class C45Util {

    public static List<String> possibleTargetValues;
    public static double threshold;
    public static double bestThreshold;

    /**
     * @Author Andre Godinez
     *
     * Gets the best attribute from a list of instances and attributes
     *
     * @param instanceList - instance list
     * @param attributes - the attribute list
     * @return the attribute with the max gain
     */
    public static Attribute bestAttribute(List<Instance> instanceList, List<Attribute> attributes) {

        double bestGain = 0.0;

        Attribute bestAttribute = null;

        for (Attribute currentAttribute: attributes) {
            double currentGain = gain(instanceList, currentAttribute);

            if(currentGain > bestGain) {
                bestGain = currentGain;
                bestAttribute = currentAttribute;

                bestThreshold = threshold;
            }
        }

        if(bestGain < 0.0) {
            return null;
        }

        return bestAttribute;
    }

    /**
     * @Author Cillian Fennell
     *
     * Calculate the information gain over instances for an attribute
     *
     * @param instanceList - the instance list
     * @param attribute - the attribute to calculate information gain
     * @return the information gain
     *
     */
    public static double gain(List<Instance> instanceList, Attribute attribute) {
        //base case
        if (instanceList.isEmpty())
            return 0.0;

        if (!attribute.isContinuous()) {
            return C45Util.entropy(instanceList) - C45Util.conditionalEntropy(instanceList, attribute);

        } else {
            List<Double> possibleThresholdValues = C45Util.calculatePossibleThresholds(instanceList, attribute);

            // Find maximum gain from among possible split points
            double maxGain = 0.0;

            for (Double currentThreshold : possibleThresholdValues) {
                double gain = C45Util.entropy(instanceList) - C45Util.conditionalEntropy(instanceList, attribute, currentThreshold);

                if (gain > maxGain) {
                    maxGain = gain;

                    threshold = currentThreshold;
                }
            }
            return maxGain;
        }
    }

    /**
     * @Author Andre Godinez
     *
     * Non conditional entropy
     *
     * @param instanceList
     * @return entropy
     *
     */
    public static double entropy(List<Instance> instanceList) {
        //base case
        if(instanceList.isEmpty())
            return 0.0;

        //get total size
        int totalSize = instanceList.size();

        //set entropy
        double entropy = 0.0;

        int count = 0;

        for (String targetValue: possibleTargetValues) {
            for (Instance currentInstance: instanceList) {
                if(currentInstance.getTargetValue().equals(targetValue)) {
                    count++;
                }
            }
            double pr = (double) count/totalSize;
            entropy += pr * log2(pr);
            count = 0;
        }

        return -(entropy);
    }


    /**
     * @Author Andre Godinez
     *
     * Gets conditional entropy for a continuous value ie.
     *
     * Entropy(type|body-length) = p(type|body-length).entropy(type|body-length)
     *
     * @param instanceList
     * @param attribute
     * @param threshold
     * @return
     *
     */
    public static double conditionalEntropy(List<Instance> instanceList, Attribute attribute, double threshold) {
        //base case
        if(instanceList.isEmpty()) {
            return 0.0;
        }

        int totalSize = instanceList.size();

        //get all instances and divide them to lessThanEqualTo threshold or greaterThan threshold

        List<Instance> lessThanEqualTo = new ArrayList<>();
        List<Instance> greaterThan = new ArrayList<>();

        for (Instance instance: instanceList) {
            double value = Double.parseDouble(instance.getAttributeValues().get(attribute.getName()));

            if(value <= threshold) {
                lessThanEqualTo.add(instance);
            }
            else {
                greaterThan.add(instance);
            }
        }

        //calculate entropy for each division
        double prLessThanEqualTo = (double)lessThanEqualTo.size()/(double)totalSize;
        double prGreaterThan = (double)greaterThan.size()/(double) totalSize;

        double entropy = (prLessThanEqualTo * C45Util.entropy(lessThanEqualTo)) + (prGreaterThan * C45Util.entropy(greaterThan));


        return entropy;
    }


    /**
     * Calculate conditional entropy for a discrete attribute
     *
     * @param instances
     * @param attribute
     * @return entropy
     *
     * Pseudocode:
     *
     * //base case
     * if instances is empty
     *  return 0
     *
     * //
     * totalSize = instances.size
     *
     * // initialise attribute value and the list of instances that have that value
     * LinkedHashMap String List <Instances> subsets
     *
     * for each possibleValue in attribute.values
     *      put(possibleValue , new Instance List)
     *
     *
     * for each instance in instanceList
     *      //get the subset we put in the attribute name is equal to for this instance
     *      //then add this instance there
     *      List<Instance> subset = subsets.get(instance.values.get(attribute.name)
     *      subset.add(instance)
     *
     * //calculate entropy for this attribute
     *
     * double entropy = 0.0
     * for each subset in subsets.values
     *      double pr = subset.size/totalsize
     *
     *      entropy+= pr*entropy(subset)
     *
     *  return entropy
     *
     *
     */
    public static double conditionalEntropy(List<Instance> instances, Attribute attribute) {
        //TODO Discrete Values

        return 0;
    }

    /**
     * @Author Andre Godinez
     */
    public static double log2(double x) {
        return x == 0.0 ? 0.0 : (Math.log(x)/ Math.log(2.0));
    }

    /**
     * @Author Cillian Fennell
     *
     * Calculate possible thresholds from an instance list given a specific attribute
     *
     * @param instanceList
     * @param attribute
     * @return list of possible thresholds
     *
     */
    public static List<Double> calculatePossibleThresholds (List<Instance> instanceList, Attribute attribute) {

        List<Double> possibleThresholdValues = new ArrayList<>();

        // add all attribute values as possible thresholds
        double currentInstanceAttributeValue;

        for (Instance instance : instanceList) {
            currentInstanceAttributeValue = Double.parseDouble(instance.getAttributeValues().get(attribute.getName()));
            possibleThresholdValues.add(currentInstanceAttributeValue);
        }

        possibleThresholdValues = possibleThresholdValues.stream().distinct().collect(Collectors.toList());
        Collections.<Double>sort(possibleThresholdValues);

        // add more thresholds by calculating the mean of 2 values beside each other
        List<Double> meanThresholdValues = new ArrayList<>();

        Iterator<Double> num1 = possibleThresholdValues.iterator();
        Iterator<Double> num2 = possibleThresholdValues.iterator();

        // skip first number for 2nd iterator
        if (num2.hasNext()) num2.next();
        while (num2.hasNext()) meanThresholdValues.add((num1.next() + num2.next()) / 2);

        // merge the two lists
        // remove all duplicates in both
        meanThresholdValues = meanThresholdValues.stream().distinct().collect(Collectors.toList());

        possibleThresholdValues.addAll(meanThresholdValues);
        Collections.sort(possibleThresholdValues);

        return possibleThresholdValues;
    }


    /**
     * @Author Andre Godinez
     *
     * Returns the majority targetvalue from the instanceList
     * @param instanceList
     * @return
     *
     *
     */
    public static String majorityTarget(List<Instance> instanceList) {
        //Map of <targetValue, count>
        Map<String, Long> count = instanceList.stream().collect(
                Collectors.groupingBy(Instance::getTargetValue, Collectors.counting()));

        //Finds max count in Map and returns the key
        String majorityTarget = count.entrySet().stream()
                                                    .max(Map.Entry.comparingByValue())
                                                    .map(Map.Entry::getKey)
                                                    .orElse("");

        return majorityTarget;
    }

    /**
     * Returns true if all instances have the same targetValue
     *
     * @return boolean
     */
    public static boolean unanimousTarget(List<Instance> instanceList) {
        String previousTargetValue = instanceList.get(0).getTargetValue();

        for (Instance currentInstance: instanceList) {
            String currentTargetValue = currentInstance.getTargetValue();

            if(!currentTargetValue.equals(previousTargetValue)) {
                return false;
            }
        }

        return true;
    }

}
