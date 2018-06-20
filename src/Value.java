import java.util.ArrayList;

/**
 * Created by Jiachen on 6/16/18.
 */
public class Value implements Node {
    private Object value;

    public Value(Object o) {
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
            return "\"" + process((String) value) + "\"";
        } else return value.toString();
    }

    private String process(String input) {
        StringBuilder strBuilder = new StringBuilder();
        for(int i = 0; i < input.length(); i++){
            char ch = input.charAt(i);
            if(ch == '\\'){
                i++;
                continue;
            }
            if(ch == '\"') strBuilder.append("\\\"");
            else strBuilder.append(ch);
        }
        return strBuilder.toString();
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

    /**
     * Recursively remove the given key value from all sub-nodes.
     *
     * @param key key value string
     * @return removed nodes
     */
    @Override
    public ArrayList<Node> remove(String key) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Node> find(String key) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }


}
