import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C45 {

    //minumum number of instances in data
    public int minInstances;

    // max Depth field
    public int maxDepth;

    // global values for targetAttribute and possible values
    public Attribute targetAttribute;
    public List<String> possibleTargetValues;

    // main decisionTree parent node with child nodes
    public Node decisionTree;

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
        targetAttribute = data.getTargetAttribute();
        possibleTargetValues = targetAttribute.getPossibleValues();

        C45Util.possibleTargetValues = possibleTargetValues;
        this.decisionTree = c45Learning(data.getInstanceList(),data.getAttributes(),data.getInstanceList());
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

        //base cases
        if(instanceList.isEmpty()) {
            return new LeafNode(C45Util.majorityTarget(parentInstances));
        }

        if(parentInstances.size() < this.minInstances ) {
            return new LeafNode(C45Util.majorityTarget(instanceList));
        }

        if(C45Util.unanimousTarget(instanceList)) {
            return new LeafNode(instanceList.get(0).getTargetValue());
        }

        //get the best attribute - attribute with most gain
        Attribute bestAttribute = C45Util.bestAttribute(instanceList,attributeList);

        if(bestAttribute.equals(null)) {
            return new LeafNode(C45Util.majorityTarget(instanceList));
        }
        
        Node root;
        double currentThreshold = C45Util.bestThreshold;

        HashMap<String, List<Instance>> subsets = new HashMap<>();
        
        if(bestAttribute.isContinuous()) {
            root = new ContinuousNode(bestAttribute.getName());
            ((ContinuousNode) root).setThreshold(currentThreshold);
            
            List<Instance> lessThanEqualTo = new ArrayList<>();
            List<Instance> greaterThan = new ArrayList<>();

            for (Instance instance: instanceList) {
                double bestAttributeValue = Double.parseDouble(instance.getAttributeValues().get(bestAttribute.getName()));
                
                if(bestAttributeValue <= currentThreshold) {
                    lessThanEqualTo.add(instance);
                } else {
                    greaterThan.add(instance);
                }
            }
            subsets.put("lessThanEqualTo", lessThanEqualTo);
            subsets.put("greaterThan", greaterThan);
        } else {
            //TODO Discrete Values
            root = new DiscreteNode();
        }

        for (Map.Entry<String, List<Instance>> entry : subsets.entrySet()) {
            List<Attribute> remainingAttributes = attributeList;

            String key = entry.getKey();
            List<Instance> subsetList = entry.getValue();

            if(!bestAttribute.isContinuous()){
                remainingAttributes.remove(bestAttribute);
            }

            Node child = c45Learning(subsetList, remainingAttributes, instanceList);

            if(bestAttribute.isContinuous()) {
                if(key.equals("lessThanEqualTo")) {
                child.setLessThanEqualTo(true);
                } else {
                child.setLessThanEqualTo(false);
                }
            } else {
                //TODO
            }
        }

        return root;
    }

    public static void main(String[] args) throws IOException {
        String[] attributes = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [BarnOwl,SnowyOwl,LongEaredOwl] target"};
        String fileName = "owls.csv";

        Data data = new Data(attributes, fileName);

        C45 classifier = new C45(0);

        classifier.train(data);

        double entropy = C45Util.entropy(data.getInstanceList());
        System.out.println(entropy);

        double conditionalEntropyContinuousTest = C45Util.conditionalEntropy(data.getInstanceList(),data.getAttributes().get(2), (double) 3.0);
        System.out.println(conditionalEntropyContinuousTest);

        System.out.println(C45Util.majorityTarget(data.getInstanceList()));

        double gain = C45Util.gain(data.getInstanceList(),data.getAttributes().get(0));
        System.out.println(gain);

        Attribute attribute = C45Util.bestAttribute(data.getInstanceList(),data.getAttributes());
        System.out.println(attribute);

    }

}
