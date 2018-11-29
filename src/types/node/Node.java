package types.node;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Andre Godinez
 *
 * Abstract class to represent types.node.Node
 *
 */
public abstract class Node {

    public List<Node> children;

    public Node() {
        children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void print(String prefix) {};
}
