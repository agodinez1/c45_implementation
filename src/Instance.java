import java.util.LinkedHashMap;

public class Instance {

    private String targetValue;
    private LinkedHashMap<String, String> attributeValues;

    public Instance(LinkedHashMap<String, String> attributeValues, String targetValue) {
        this.targetValue = targetValue;
        this.attributeValues = attributeValues;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public LinkedHashMap<String, String> getAttributeValues() {
        return attributeValues;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "targetValue='" + targetValue + '\'' +
                ", attributeValues=" + attributeValues +
                '}';
    }
}
