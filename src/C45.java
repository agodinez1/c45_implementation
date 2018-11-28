import java.io.IOException;
import java.util.*;

public class C45 {

    // max Depth field
    public int maxDepth;

    // global values for targetAttribute and possible values
    public Attribute targetAttribute;
    public List<String> possibleTargetValues;

    public Node decisionTree;

    /**
     * C45 constructor
     *
     * @param maxDepth - maximum number of depth the decisionTree Node can have
     */
    public C45 (int maxDepth) {
        this.maxDepth = maxDepth;
        //TODO maybe include max depth in the algorithm
    }

    public void printDecisionTree() {
       this.decisionTree.print("");
    }

    public Node getDecisionTree(){
        return decisionTree;
    }

    /**
     * Training function given data of instances and attributes
     *
     * @param data - the data object containing the attributes and instances
     *
     */
    public void train(Data data) {
        targetAttribute = data.getTargetAttribute();
        possibleTargetValues = targetAttribute.getPossibleValues();

        C45Util.possibleTargetValues = possibleTargetValues;

        this.decisionTree = c45Learning(data.getInstanceList(),data.getAttributes(),data.getInstanceList());
    }

    /**
     * Testing function that returns the accuracy of c45 decision tree.
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
    public double test(List<Instance> instanceList, Node node) {

        long count = instanceList.stream()
                                    .filter(x -> x.getTargetValue().equals(predict(x, node)))
                                    .count();
        double accuracy =  (double) count / instanceList.size();
        return accuracy;
    }

    /**
     * Testing function shuffles the instane list and splits it into test and training datasets.
     *
     * @param data - input data from file
     *
     */
    public double crossValidation(Data data, int n){

        //Fetch instanceList from input data
        List<Instance> instanceList = data.getInstanceList();
        List<Attribute> attributeList = data.getAttributes();

        //Shuffle list
        Collections.shuffle(instanceList);

        //Splitting instances into three sub lists
        int first = (int) Math.ceil(instanceList.size() * 0.33);
        int second = (int) Math.ceil(instanceList.size() * 0.66);
        int third = instanceList.size();

        List<Instance> one = instanceList.subList(0, first);
        List<Instance> two = instanceList.subList(first, second);
        List<Instance> three = instanceList.subList(second, third);

        List<Instance> training = new ArrayList<>();
        List<Instance> test = new ArrayList<>();

        int j = n % 3;

        switch(j){
            case 0:
                test = one;
                training.addAll(two);
                training.addAll(three);
                break;
            case 1:
                training.addAll(one);
                test = two;
                training.addAll(three);
                break;
            case 2:
                training.addAll(one);
                training.addAll(two);
                test = three;
                break;
        }

        //Use training dataset to fit the model
        Node node = c45Learning(training, attributeList, training);
        double accuracy = test(test, node);

        return accuracy;
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
        if(node instanceof LeafNode){
            return ((LeafNode) node).targetValue;
        }

        if(node instanceof ContinuousNode){
            double instanceValue = Double.parseDouble(instance.getAttributeValues().get(((ContinuousNode) node).getName()));

            if(instanceValue <= ((ContinuousNode) node).getThreshold()){
                return predict(instance, node.children.get(0));
            }else{
                return predict(instance, node.children.get(1));
            }

        }else{
            //TODO Discrete values
        }

        return null;
    }


    /**
     * Creates a decision tree with child nodes given an instanceList and attributeList
     *
     * @param instanceList
     * @param attributeList
     * @param parentInstances
     * @return
     *
     */
    public Node c45Learning(List<Instance> instanceList, List<Attribute> attributeList, List<Instance> parentInstances) {

        //base cases
        if(instanceList.isEmpty()) {
            return new LeafNode(C45Util.majorityTarget(parentInstances));
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

            subsets = C45Util.subsetInstanceListContinuous(bestAttribute,instanceList,currentThreshold);

        } else {
            //TODO - Discrete Values lessThanEqualTo will be null here
            root = new DiscreteNode();
        }

        for (Map.Entry<String, List<Instance>> entry : subsets.entrySet()) {
            List<Attribute> remainingAttributes = attributeList;

            String key = entry.getKey();
            List<Instance> subsetList = entry.getValue();

            if(bestAttribute.isContinuous()) {
                Node child = c45Learning(subsetList, remainingAttributes, instanceList);
                root.addChild(child);
            } else {
                remainingAttributes.remove(bestAttribute);
                //TODO : Create different node child for discrete value
            }
        }

        //TODO stream ^^

        return root;
    }

    public static void main(String[] args) throws IOException {
        String[] attributes = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [BarnOwl,SnowyOwl,LongEaredOwl] target"};
        String fileName = "owls.csv";

        Data data = new Data(attributes, fileName);

        C45 classifier = new C45(0);

        classifier.train(data);
        classifier.printDecisionTree();
        classifier.test(data.getInstanceList(), classifier.getDecisionTree());

        // Testing with random instance value
        LinkedHashMap<String, String> avp = new LinkedHashMap<>();

        avp.put("body-length", "3");
        avp.put("wing-length","6.1");
        avp.put("body-width","4.5");
        avp.put("wing-width", "1");

        Instance test = new Instance(avp, "Test");

        String predictedValue = classifier.predict(test,classifier.getDecisionTree());

        System.out.println(predictedValue);

        for(int i = 1; i <= 10; i++)
            System.out.println("Tree accuracy : " + classifier.crossValidation(data, i));

    }

}
