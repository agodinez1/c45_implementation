import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    private List<Node> children;
    private boolean lessThanEqualTo;

    public Node() {
        children = new ArrayList<>();
    }

    public Node getChild(int index) {
        return this.children.get(index);
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void setLessThanEqualTo(boolean lessThanEqualTo) {
        this.lessThanEqualTo = lessThanEqualTo;
    }
}
