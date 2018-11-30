package types.node;

/**
 * @Author Andre Godinez
 *
 * Leaf node that extends a the abstract class node
 *
 * Leaf nodes only have a targetValue and have no children - no super call
 *
 */
public class LeafNode extends Node {

    public String targetValue;

    public LeafNode(String targetValue) {
        this.targetValue = targetValue;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix  + "|__  " + targetValue);
    };
}
