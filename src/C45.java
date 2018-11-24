import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class C45 {

    // minumum number of instances in data
    public int minInstances;

    // max Depth field
    public int maxDepth;

    // global values for targetAttribute and possible values
    public Attribute targetAttribute;
    public List<String> possibleTargetValues;

    // main decisionTree parent node with child nodes
    public Node decisionTree;

    // global temporary variables to store threshold when calculating
    public double threshold;
    public double bestThreshold;

    /**
     * C45 constructor
     *
     * @param maxDepth - maximum number of depth the decisionTree Node can have
     */
    public C45 (int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void printDecisionTree() {
        //TODO
    }

    /**
     * Training function
     *
     * @param data - the data object containing the attributes and instances
     *
     * Pseudocode:
     * for all Attributes in data
     *             get the targetAttribute
     *             set the targetAttribute
     * set possibleTargetValues
     *
     * set decisionTree = c45Learning(data.instances, data.attributes, data.instances)
     * print decisionTree
     *
     */
    public void train(Data data) {
        for (Attribute attribute: data.getAttributes()) {
            if(attribute.isTarget()) {
                targetAttribute = attribute;
                possibleTargetValues = attribute.getPossibleValues();
            }
        }

        //TODO: call c45Learning here
    }

    /**
     * Testing function that prints out the accuracy of c45 decision tree.
     *
     * @param instanceList - the list of instances containing test data
     *
     * Pseudocode:
     * set predicted = 0;
     *
     * for each instance in instanceList
     *               predicted = predict(instance, decisionTree)
     *               if predicted = instance.targetValue
     *                     predicted++
     *
     * set accuracy = predicted / instanceList.size
     *
     */
    public void test(ArrayList<Instance> instanceList) {
        //TODO
    }

    /**
     * NB - Only works if the decision tree is built
     *
     * Predicts the targetValue given the instance and the decisionTree
     *
     * @param instance
     * @param node
     * @return the predicted targetValue
     *
     * Pseudocode:
     * if the node is a leafnode
     *      return leafnode targetValue
     *
     * if the node is continuous
     *
     *      instanceValue = the attribute value the node is looking for i.e. node = body-length < 1 - gets body-length value
     *
     *      if(instanceValue <= node.threshold)
     *          // travel to the left of that node
     *          return classify(instance, node.getChild(0))
     *      else
     *          return classify(instance, node.getChild(1))
     *
     * // node is discrete
     * else
     *      instanceValue = (attribute value = node.attribute)
     *      for each child in node.getChildren
     *
     *      if(child.value == instanceValue)
     *          return classify(instance, child)
     *
     * return null
     */
    public String predict(Instance instance, Node node) {
        //TODO

        return "";
    }


    /**
     * Seems like a big function, we might need to separate some functionalities into its own function
     *
     * Creates a decision tree with child nodes given an instanceList and attributeList
     *
     * @param instanceList
     * @param attributeList
     * @param parentInstances
     * @return
     *
     * Pseudocode:
     *
     * // base cases
     *
     *      // dont know how this one works honestly
     *      if the instanceList list is empty
     *          return Node(majorityTarget(parentInstances), true) // true is for leafNode
     *
     *      if (instances.size < minInstances)
     *          return Node(majorityTarget(instances), true) // leafNode
     *
     *      if (unanimousTarget(instances))
     *          return Node(instances.get(0).targetValue, true) // just get any targetValue , leafNode
     *
     *      //get the best attribute
     *      bestAttribute = bestAttribute(instances, attributes)
     *
     *      if(bestAttribute == null)
     *          return Node(majorityTarget(instances), true)
     *
     *      //current Node we are creating
     *      root = Node(bestAttribute.name, false) // not a leaf node
     *
     *      //divide instances based on best attribute i.e.
     *
     *      //bestThreshold is set from bestAttribute function
     *      currentThreshold = bestThreshold
     *
     *      Hashmap String, List<Instances> subsets
     *
     *      if(bestAttribute is continuous)
         *          root.continuous = true
         *          root.threshold = currentThreshold
         *
         *          lessThanEqualTo = new List
         *          greaterThan = new List
         *
         *      foreach instance in instanceList
         *          value = value of bestAttribute at instance
         *              if (value <= threshold)
         *                  add this instance to lessThanEqualTo
         *              else
         *                  add this instance to greaterThan
         *      subsets.put("lessThanEqualTo", lessthanEqualTo)
         *      subsets.put("greaterThan", greaterThan)
     *
     *      else // best attribute is discrete
         *          for each possibleValue of bestAttribute
         *              subsets.put(value, List<instance>) // initialize list to store values
         *
         *          for each instance in instances
         *              get the list of instances that has the value of the value of thebestAttribute in current instance
         *              List<Instance> subset = subsets.get(instance.values.get(bestAttribute.name))
         *              subset.add(instance)
     *
     *    // so now we have subsets which are <String, List<instance>> i.e. ("greaterThan" , instance[2],instance[5]...)
     *
     *    for each key in subsets
     *
     *          subsetInstanceList = subsets.get(key)
     *
     *          get remainingAttributes
     *
     *          if bestAttribute is discrete
     *              remainingAttributes.remove(bestAttribute)
     *
     *          child = Node(subsetInsanceList, remainingAttributes, instances)
     *
     *          // this is just for printing out purposes
     *          if bestAttribute is continuous
     *              if(key equals lessThanEqualTo
     *                  child.isLET = true;
     *              else
     *                  set false
     *
     *          else
     *              child.value = key
     *
     *          root.addChild(child)
     *
     *    return root
     *
     */
    public Node c45Learning(List<Instance> instanceList, List<Attribute> attributeList, List<Instance> parentInstances) {
        //TODO
        return null;
    }

    /**
     * Gets the best attribute from a list of instances and attributes
     *
     * @param instances - instance list
     * @param attributes - the attribute list
     * @return the attribute with the max gain
     *
     * Pseudocode:
     *
     *  set maxGain = 0.0
     *  Attribute best = null;
     *
     *  for each attribute in attributes
     *      gain = gain(instances, attribute)
     *          if( gain > maxGain)
     *
     *              maxGain = gain
     *              best = attribute
     *
     *              bestThreshold = threshold
     *
     *  if maxGain < 0
     *      return null
     *
     *
     *  return best
     */
    private Attribute bestAttribute(List<Instance> instances, List<Attribute> attributes) {
        //TODO

        return null;
    }

    /**
     * Calculate the information gain over instances for an attribute
     *
     * @param instances - the instance list
     * @param attribute - the attribute to calculate information gain
     * @return the information gain
     *
     * Pseudocode:
     *
     * //base case
     * if instances is Empty
     *      return 0
     *
     * if attribute is discrete
         *         return entropy(instances) - conditionalEntropy(instances, attribute)
     * else
     *      //calculate the possible thresholds
     *
     *      //This makes what ever we add in order
     *      TreeSet<Double> values = new treeset
     *
     *      for each instance
     *          attributevalue = instance.values.get(attribute.name)
     *
     *          values.add(attributevalue)
     *
     *      //the way they get the thresholds is getting the getting the mean between to values
     *      // i.e.  4.2 , 4.1 -> candidateThreshold = 4.15
     *      //
     *      // we can do it like this or we can just use the values we have above
     *
     *      List<Double> candidateThresholds ...
     *
     *      Iterator num1 = values.iterator
     *      iterator num2 = values.iterator
     *
     *      //give num2 a headstart
     *      if(num2.hasNext)
     *          num2.next()
     *      while(num2 hasNext)
     *          candidateThresholds.add(num1.next + num2.next / 2)
     *
     *      //find Maximum gain from candidateThresholds
     *      double maxGain = 0.0
     *      double threshold = 0.0
     *
     *      for each threshold in candidateThresholds
     *          gain = entropy(instances) - conditionalEntropy(instances, attribute, threshold)
     *
     *          if(gain > maxGain)
     *              maxGain = gain
     *              this.threshold = threshold;
     *
     *
     *      return maxGain
     *
     */
    private double gain(List<Instance> instances, Attribute attribute) {
        //TODO

        return 0;
    }

    /**
     * // the example only has 2 possible target values while we have 3 for the owl
     * // need to change the pseudocode slightly
     *
     *
     * Non conditional entropy
     * @param instanceList
     * @return entropy
     *
     * Pseudocode:
     *
     * //basecase
     * if instances empty
     *  return 0
     *
     * // get totalsize
     * entropy = 0.0;
     * total = instances size
     *
     * int count = 0;
     *
     * for each possibleTargetValue in possibleTargetValues
     *
     *      for each instance in instances
     *
     *          if(instance.label equals possibleTargetValue)
     *          count++
     *
     *          entropy+= (count/total) * logBase2(count/total)
     *
     *   //reset count to 0 for every possibleTargetValue
     *   count = 0;
     *
     *
     *  return -(entropy)
     *
     *  //hopefully the pseudocode works
     */
    private double entropy(List<Instance> instanceList) {
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
            entropy += pr*log2(pr);
            count = 0;
        }
       return -(entropy);
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
    private double conditionalEntropy(List<Instance> instances, Attribute attribute) {
        //TODO

        return 0;
    }

    /**
     * //TODO
     * @param instances
     * @param attribute
     * @param threshold
     * @return
     *
     * Pseudocode:
     *
     * similar to what we have above
     *
     * //base case
     * instance empty
     * return 0
     *
     * //get all instances and partition them to lessThanEqualTo or greaterThan threshold
     * totalsize = instances size
     *
     * List Instance lessThanEqualto
     * List Instance greaterThan
     *
     * for each instance in instances
     *      value is intstance.get(attribute.name)
     *
     *      if(value<=threshold)
     *          add instance to lessThanEqualto
     *
     *      else
     *          add instane to greaterThan
     *
     *  //calculate entropy
     *  prLessthanEqualTo = lessThanEqualto.size / totalsize
     *  prGreaterThan = greaterThan.size / totalsize
     *
     *  double entropy = prGreaterThanEqualTo * entropy(lessThanEqualto)
     *      prGreaterThan * entropy(greaterThan)
     *
     *  return entropy
     */
    private double conditionalEntropy(List<Instance> instances, Attribute attribute, double threshold) {
        //TODO

        return 0;
    }

    private double log2(double x) {
        return x == 0.0 ? 0.0 : (Math.log(x)/ Math.log(2.0));
    }


    /**
     * Returns the majority targetvalue from the instanceList
     * @param instanceList
     * @return
     *
     *
     */
    private String majorityTarget(List<Instance> instanceList) {
        //TODO

        return null;
    }

    /**
     * Returns true if all isntance have the same targetValue
     * @return
     */
    private boolean unanimousTarget() {
        //TODO

        return false;
    }

    public static void main(String args[]) throws IOException {
        String[] attributes = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [BarnOwl,SnowyOwl,LongEaredOwl] target"};
        String fileName = "owls.csv";

        Data data = new Data(attributes, fileName);

        C45 c45 = new C45(0);
        c45.train(data);
        double entropy = c45.entropy(data.getInstanceList());
        System.out.println(entropy);
    }

}
