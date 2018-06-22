import java.util.ArrayList;

/**
 * Created by Jiachen on 6/16/18.
 */
@SuppressWarnings("unused")
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
    ArrayList<Node> removeAll(String key);

    ArrayList<Node> find(String key);

    void replaceAll(String key, Mapper<Node> mapper);

    boolean isEmpty();

    Node get(String key);

    static ArrayList<Node> removeAll(Node node, String... keys) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (String key : keys) {
            nodes.addAll(node.removeAll(key));
        }
        return nodes;
    }
}
