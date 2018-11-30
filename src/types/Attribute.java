package types;

import java.util.List;

/**
 * @Author Andre Godinez
 *
 * types.Attribute class
 *
 */
public class Attribute {

    // attribute name
    private String name;

    // list possible values for this attribute
    private List<String> possibleValues;

    // if the attribute is continuous
    private boolean isContinuous;

    // if the attribute is the target attribute
    private boolean isTarget;

    //threshold associated with this attribute if any
    private double threshold;

    /**
     * types.Attribute constructor
     *
     * @param name
     * @param possibleValues
     * @param isTarget
     */
    public Attribute(String name, List<String> possibleValues, boolean isTarget) {
        this.name = name;
        this.possibleValues = possibleValues;
        this.isTarget = isTarget;
        this.isContinuous = this.possibleValues.size() > 1 ? false: true;
        this.threshold = getThreshold();
    }

    public String getName() {
        return this.name;
    }

    public List<String> getPossibleValues() {
        return this.possibleValues;
    }

    public boolean isContinuous() {
        return this.isContinuous;
    }

    public boolean isTarget() {
        return this.isTarget;
    }

    public double getThreshold(){
        return threshold;
    }

    public void setThreshold(double threshold){
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "types.Attribute{" +
                "name='" + name + '\'' +
                ", possibleValues=" + possibleValues +
                ", isContinuous=" + isContinuous +
                '}';
    }
}
