
import java.util.ArrayList;

/**
 * Created by Jiachen on 6/16/18.
 */
public class List implements Node {
    private ArrayList<Node> items;

    List() {
        items = new ArrayList<>();
    }

    void add(Node item) {
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
        if ( items.containsAll(otherItems) && otherItems.containsAll(items))
            return true;
        return false;
    }
}
