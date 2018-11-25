public class LeafNode extends Node {

    public String targetValue;

    public LeafNode(String targetValue) {
        this.targetValue = targetValue;
    }

    @Override
    public String toString() {
        return "LeafNode{" +
                "targetValue='" + targetValue + '\'' +
                '}';
    }
}
