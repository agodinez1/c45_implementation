package test;

import data.Instance;
import data.ProcessInput;

import java.io.IOException;
import java.util.*;

public class CrossValidation {
    ProcessInput p;
    ArrayList<Instance> instances;

    public CrossValidation(ProcessInput p){
        this.p = p;
        this.instances = p.getInstances();
    }

    private HashMap splitList(ArrayList<Instance> instances){
        //Collections.shuffle(instances);
        int trainingUpper = (int) Math.floor(instances.size() * 0.66);
        List<Instance> training = instances.subList(0, trainingUpper);
        List<Instance> test = instances.subList(trainingUpper + 1, instances.size() - 1);

        HashMap<String, List<Instance>> map = new HashMap<String, List<Instance>>();
        map.put("training", training);
        map.put("test", test);

        return map;
    }

    public ArrayList<Instance> getInstances() {
        return this.instances;
    }

    public static void main(String[] args) throws IOException{
        String fileName = "owls.csv";
        String[] attributes = { "body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [Barn_Owl,Snowy_Owl,Long-Eared_Owl] target" };
        ProcessInput pi = new ProcessInput(attributes, fileName);

        CrossValidation cv = new CrossValidation(pi);
        //System.out.println(Arrays.asList(cv.splitList(cv.getInstances())));

        cv.splitList(cv.getInstances()).forEach((k, v) -> System.out.println("Key : " + k + " , Value : " + v));

    }

}
