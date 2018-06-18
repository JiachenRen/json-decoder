
/**
 * Created by Jiachen on 6/16/18.
 */
public class Value implements Node {
    private Object value;

    Value(Object o) {
        this.value = o;
    }

    Object get() {
        return value;
    }

    @Override
    public NodeType getType() {
        return NodeType.VALUE;
    }

    @Override
    public String encode() {
        if (value == null) return "null";
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else return value.toString();
    }

    @Override
    public String stringValue() {
        return value.toString();
    }

    public String toString() {
        return stringValue();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Value)) return false;
        return encode().equals(((Value) other).encode());
    }
}
