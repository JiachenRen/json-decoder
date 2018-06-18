
import java.util.HashMap;

/**
 * Created by Jiachen on 6/16/18.
 */
public class Dictionary implements Node {
    private HashMap<String, Node> entries;

    Dictionary() {
        entries = new HashMap<>();
    }

    void define(String key, Node value) {
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
}
