package test;

import data.Instance;
import data.ProcessInput;

import java.io.IOException;
import java.util.*;

public class CrossValidation {

    private ProcessInput p;
    private ArrayList<Instance> instances;

    public CrossValidation(ProcessInput p){
        this.p = p;
        this.instances = p.getInstances();
    }

    private HashMap splitLists(int n){

        HashMap<String, HashMap<Integer, List<Instance>>> parentMap = new HashMap<>();
        HashMap<Integer, List<Instance>> trainingMap = new HashMap<>();
        HashMap<Integer, List<Instance>> testMap = new HashMap<>();

        for(int i = 1; i <= n; i++) {
            Collections.shuffle(instances);

            //Splitting instances into training and test datasets
            int trainingUpper = (int) Math.floor(instances.size() * 0.66);
            List<Instance> training = instances.subList(0, trainingUpper);
            List<Instance> test = instances.subList(trainingUpper, instances.size() - 1);

            //Populating maps


            trainingMap.put(i, training);
            testMap.put(i, test);

            parentMap.put("training", trainingMap);
            parentMap.put("test", testMap);
        }

        for(Map.Entry<String, HashMap<Integer,List<Instance>>> t :parentMap.entrySet()){
            String key = t.getKey();
            for (Map.Entry<Integer,List<Instance>> e : t.getValue().entrySet())
                System.out.println("Dataset: " + key + " Iteration: " + e.getKey()+ " Instances: " +e.getValue());
        }

        return parentMap;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "owls.csv";
        String[] attributes = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [Barn_Owl,Snowy_Owl,Long-Eared_Owl] target"};
        ProcessInput pi = new ProcessInput(attributes, fileName);

        CrossValidation cv = new CrossValidation(pi);

        //Printing HashMap
        //System.out.println(Arrays.asList(cv.splitLists(10)));
        cv.splitLists(10);
    }

}
