import java.util.List;

public class Attribute {

    private String name;
    private List<String> possibleValues;
    private boolean isContinuous;
    private boolean isTarget;

    public Attribute(String name, List<String> possibleValues, boolean isTarget) {
        this.name = name;
        this.possibleValues = possibleValues;
        this.isTarget = isTarget;
        this.isContinuous = this.possibleValues.size() > 1 ? false: true;
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

    @Override
    public String toString() {

        return "Attribute{" +
                "name='" + name + '\'' +
                ", possibleValues=" + possibleValues +
                ", isContinuous=" + isContinuous +
                '}';
    }
}
