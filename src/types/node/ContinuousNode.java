package types.node;

/**
 * @Author Andre Godinez
 *
 * Continuous nodes extend the abstract class node
 *
 * Continuous nodes have a threshold value and the name of the attribute it represents
 * super call because Continuous nodes have children
 *
 */
public class ContinuousNode extends Node {

    private double threshold;
    private String name;

    public ContinuousNode(String name) {
        super();
        this.name = name;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold(){
        return threshold;
    }

    public String getName(){
        return name;
    }

    @Override
    public void print(String prefix) {
        int index = 0;
        for(Node child: children) {
            String name = this.name;
            if(index == 0) {
                name += "<=";
            } else {
                name += ">";
            }
            name += threshold;
            System.out.println(prefix + "├── " + name);
            child.print(prefix +  "|        ");

            index++;
        }
    }


}
