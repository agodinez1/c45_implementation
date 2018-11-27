import java.util.ArrayList;
import java.util.List;


public abstract class Node {

    protected List<Node> children;

    public Node() {
        children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void print(String prefix) {};
}
