import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Data {

    private ArrayList<Instance> instanceList;
    private ArrayList<Attribute> attributes;

    public Data(String[] attributesArray, String fileName) throws IOException {
        this.attributes = processAttributes(attributesArray);
        this.instanceList = processInstanceList(fileName);
    }

    private ArrayList<Instance> processInstanceList(String fileName) throws IOException {
        Scanner scanner = new Scanner(new File(fileName));
        ArrayList<Instance> instanceList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            ArrayList<String> attributeValues = new ArrayList<>(Arrays.asList(line.split(",")));
            String targetValue = attributeValues.get(attributeValues.size() - 1);

            LinkedHashMap<String, String> attributeValuePairs = new LinkedHashMap<>();

            for (int i = 0; i < attributeValues.size(); i++) {
                attributeValuePairs.put(this.attributes.get(i).getName(), attributeValues.get(i));
            }

            instanceList.add(new Instance(attributeValuePairs, targetValue));
        }
        return instanceList;
    }

    private ArrayList<Attribute> processAttributes(String[] attributesArray) throws IOException {

        ArrayList<Attribute> attributes = new ArrayList<>();

        // add all attributes to arraylist of attributes
        for (int i = 0; i < attributesArray.length; i++) {

            // split the string by all white space i.e. Length real becomes ["Length",
            // "Real", "Target"].
            String[] attribute_value = attributesArray[i].split(" ");

            // if attribute_value length is not equal to 2 the format is wrong.
            if (attribute_value.length != 3)
                throw new IOException("Invalid attribute format");

            // name
            String name = attribute_value[0];

            // possible values
            List<String> possibleValues = new ArrayList<String>();
            if (attribute_value[1].equals("real")) {
                possibleValues.add("real");
            } else {
                String[] possibleValuesArr = attribute_value[1].substring(1, attribute_value[1].length() - 1).split(",");
                for (String possibleValue : possibleValuesArr) {
                    possibleValues.add(possibleValue);
                }
            }

            // is target
            boolean isTarget = attribute_value[2].equals("target") ? true : false;

            // create a new attribute object and add to the attribute set
            Attribute attr = new Attribute(name, possibleValues, isTarget);

            attributes.add(attr);
        }

        return attributes;
    }

    @Override
    public String toString() {
        return "Data{" +
                "instanceList=" + instanceList +
                ", attributes=" + attributes +
                '}';
    }

    public static void main(String[] args) throws IOException {
        String[] attributes = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
                "type [Barn_Owl,Snowy_Owl,Long-Eared_Owl] target"};
        String fileName = "owls.csv";

        Data data = new Data(attributes, fileName);

        System.out.println(data);
    }

}
