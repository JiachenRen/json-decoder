import java.util.ArrayList;

/**
 * Created by Jiachen on 6/16/18.
 */
public class List implements Node {
    private ArrayList<Node> items;

    public List() {
        items = new ArrayList<>();
    }

    public void add(Node item) {
        items.add(item);
    }

    public ArrayList<Node> getItems() {
        return items;
    }

    @Override
    public NodeType getType() {
        return NodeType.LIST;
    }

    @Override
    public String encode() {
        return "[" + items.stream()
                .map(Node::encode)
                .reduce((a, b) -> a + "," + b)
                .orElse("") + "]";
    }

    @Override
    public String stringValue() {
        return null;
    }

    public String toString() {
        return encode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof List)) return false;
        ArrayList<Node> otherItems = ((List) other).items;
        return items.containsAll(otherItems) && otherItems.containsAll(items);
    }

    /**
     * Recursively remove the given key value from all sub-nodes.
     *
     * @param key key value string
     * @return removed nodes
     */
    @Override
    public ArrayList<Node> removeAll(String key) {
        ArrayList<Node> nodes = new ArrayList<>();
        items.forEach(item -> nodes.addAll(item.removeAll(key)));
        return nodes;
    }

    @Override
    public ArrayList<Node> find(String key) {
        ArrayList<Node> nodes = new ArrayList<>();
        items.forEach(item -> nodes.addAll(item.find(key)));
        return nodes;
    }

    @Override
    public void replaceAll(String key, Mapper<Node> mapper) {
        items.forEach(item -> item.replaceAll(key, mapper));
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public Node get(String key) {
        return null;
    }
}
