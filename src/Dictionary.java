import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Jiachen on 6/16/18.
 */
@SuppressWarnings("WeakerAccess")
public class Dictionary implements Node {
    private LinkedHashMap<String, Node> entries;

    public Dictionary() {
        entries = new LinkedHashMap<>();
    }

    public void define(String key, Node value) {
        entries.put(key, value);
    }

    public Node get(String key) {
        return entries.get(key);
    }

    @Override
    public NodeType getType() {
        return NodeType.DICTIONARY;
    }

    @Override
    public String encode() {
        return "{" + entries.keySet().stream()
                .map(key -> "\"" + key + "\":" + entries.get(key).encode())
                .reduce((a, b) -> a + "," + b)
                .orElse("") + "}";
    }

    @Override
    public String stringValue() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Dictionary)) return false;
        Dictionary dict = ((Dictionary) other);
        for (String key : entries.keySet())
            if (!dict.get(key).equals(get(key)))
                return false;
        return true;
    }

    public String toString() {
        return encode();
    }

    public ArrayList<Node> removeAll(String key) {
        ArrayList<Node> nodes = new ArrayList<>();
        Node removed = entries.remove(key);
        if (removed != null) {
            nodes.add(removed);
        }
        entries.forEach((str, node) -> nodes.addAll(node.removeAll(key)));
        return nodes;
    }

    @Override
    public ArrayList<Node> find(String key) {
        ArrayList<Node> nodes = new ArrayList<>();
        Node match = entries.get(key);
        if (match != null) {
            nodes.add(match);
        }
        entries.forEach((str, node) -> nodes.addAll(node.find(key)));
        return nodes;
    }

    @Override
    public void replaceAll(String key, Mapper<Node> mapper) {
        entries.forEach((str, node) -> node.replaceAll(key, mapper));
        Node oldEntry = entries.get(key);
        if (oldEntry != null) { // Entry does exist
            entries.replace(key, mapper.map(oldEntry));
        }
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
