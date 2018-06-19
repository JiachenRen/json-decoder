import java.util.ArrayList;

/**
 * Created by Jiachen on 6/16/18.
 */
public interface Node {
    NodeType getType();

    String encode();

    String stringValue();

    String toString();

    boolean equals(Object other);

    /**
     * Recursively remove the given key value from all sub-nodes.
     *
     * @param key key value string
     * @return removed nodes
     */
    ArrayList<Node> remove(String key);

    ArrayList<Node> find(String key);

    boolean isEmpty();

    static ArrayList<Node> remove(Node node, String... keys) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (String key : keys) {
            nodes.addAll(node.remove(key));
        }
        return nodes;
    }
}
