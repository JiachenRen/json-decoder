
/**
 * Created by Jiachen on 6/16/18.
 */
public interface Node {
    NodeType getType();
    String encode();
    String stringValue();
    String toString();
    boolean equals(Object other);
}
