import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class C45 {

    // global values for targetAttribute and possible values
    public Attribute targetAttribute;
    public List<String> possibleTargetValues;

    public Node decisionTree;

    // minimum instances a decide a node
    public int minimumInstances;

    // maximum depth of decision tree
    public int maxDepth;

    /**
     * Default constructor
     */
    public C45() {
        this.minimumInstances = 10;
        this.maxDepth = 0;
    }

    public C45 (int minimumInstances, int maxDepth) {
        this.minimumInstances = minimumInstances;
        this.maxDepth = maxDepth;
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

        this.decisionTree = c45Learning(data.getInstanceList(),data.getAttributes(),data.getInstanceList(),0);
    }

    /**
     * Testing function that returns the accuracy of c45 decision tree.
     *
     * @param instanceList - the list of instances containing test data
     *
     */
    public double accuracy(List<Instance> instanceList, Node node) {

        long count = instanceList.stream()
                                    .filter(x -> x.getTargetValue().equals(predict(x, node)))
                                    .count();

        double accuracy =  (double) count / instanceList.size();
        return accuracy;
    }


    public void outputActualPredicted(List<Instance> instanceList, Node node, int iteration, PrintWriter out) throws FileNotFoundException, UnsupportedEncodingException {
        out.println("=========================== Iteration " + (iteration + 1) +"============================");
        out.println(String.format("%-20s %-20s", "Actual", "Predicted"));

        String actual;
        String predicted;

        for (Instance instance: instanceList) {
            actual = instance.getTargetValue();
            predicted = predict(instance, node);

            if(actual.equals(predicted)) {
                out.println(String.format("%-20s %-20s", actual, predicted));
            } else {
                out.println(String.format("%-20s %-20s             X", actual, predicted));
            }

        }
    }

    /**
     * @Author Cillian Fennell
     *
     * Testing function shuffles the instance list and splits it into test and training datasets.
     *
     * @param data - input data from file
     * @param n - num times to do cross validation
     * @return HashMap of list of accuracies and average accuracy
     *
     */
    public HashMap<List<Double>,Double> crossValidation(Data data, int n) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter  out = new PrintWriter("actual_predicted.txt", "UTF-8");
        HashMap<List<Double>, Double> accuracies_average = new HashMap<>();

        //Fetch instanceList from input data
        List<Instance> instanceList = data.getInstanceList();
        List<Attribute> attributeList = data.getAttributes();

        int first = (int) Math.ceil(instanceList.size() * 0.33);
        int second = (int) Math.ceil(instanceList.size() * 0.66);
        int third = instanceList.size();

        ArrayList<Double> accuracies = new ArrayList<>();
        double accuracy;

        for(int i = 0; i < n; i++) {
            //Shuffle list
            Collections.shuffle(instanceList);

            //Splitting instances into three sub lists
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
            Node node = c45Learning(training, attributeList, training, 0);

            accuracy = accuracy(test, node);
            outputActualPredicted(test,node,i,out);

            System.out.println("Iteration " + (i + 1) + ": " + accuracy);
            accuracies.add(accuracy);
        }

        Double averageAccuracy = accuracies.stream().mapToDouble(val -> val).average().orElse(0.0);
        System.out.println("Average accuracy in "+ n +" iterations: " + averageAccuracy);
        accuracies_average.put(accuracies, averageAccuracy);

        System.out.println("Flushing...");
        out.flush();
        out.close();
        System.out.println("Done");
        return accuracies_average;
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
    public Node c45Learning(List<Instance> instanceList, List<Attribute> attributeList, List<Instance> parentInstances, int depth) {

        //base cases
        if(instanceList.isEmpty()) {
            return new LeafNode(C45Util.majorityTarget(parentInstances));
        }

        if(instanceList.size() < minimumInstances) {
            return new LeafNode(C45Util.majorityTarget(instanceList));
        }

        if(depth == maxDepth && maxDepth != 0) {
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
                Node child = c45Learning(subsetList, remainingAttributes, instanceList , depth + 1 );
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

        C45 classifier = new C45(10,3);

        classifier.train(data);
        classifier.printDecisionTree();
        classifier.accuracy(data.getInstanceList(), classifier.getDecisionTree());

        // Testing with random instance value
        LinkedHashMap<String, String> avp = new LinkedHashMap<>();

        avp.put("body-length", "3");
        avp.put("wing-length", "6.1");
        avp.put("body-width", "4.5");
        avp.put("wing-width", "1");

        Instance test = new Instance(avp, "Test");

        String predictedValue = classifier.predict(test, classifier.getDecisionTree());

        System.out.println(predictedValue);

        HashMap<List<Double>, Double> accuracies_average = classifier.crossValidation(data, 10);
    }
}
