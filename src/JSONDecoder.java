import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiachen on 6/16/18.
 */
public class JSONDecoder {
    private static ArrayList<String> dict;

    public static Node decode(String json) throws JSONDecodeException {
        dict = new ArrayList<>();
        String processed = process(json);
//        System.out.println(processed);
        return parse(processed);
    }

    private static Node parse(String processed) throws JSONDecodeException {
        if (processed.startsWith("{")) return genDict(processed);
        else if (processed.startsWith("[")) {
            return genList(processed);
        }
        throw new JSONDecodeException("invalid JSON string");
    }

    private static List genList(String processed) throws JSONDecodeException {
        List list = new List();
        while (processed.contains("{")) {
            int startIdx = processed.indexOf("{");
            int endIdx = match(processed, startIdx, '}');
            String dict = processed.substring(startIdx, endIdx + 1);
            list.add(parse(dict));
            char c = processed.charAt(endIdx + 1);
            if (c == ']') break;
            else if (c == ',') processed = processed.substring(endIdx + 2);
            else throw new JSONDecodeException("syntax error");
        }
        return list;
    }

    private static Dictionary genDict(String processed) throws JSONDecodeException {
        Dictionary dictionary = new Dictionary();
        int curIdx = 1, matchingIdx = 0, closingIdx = match(processed, 0, counterpart(processed.charAt(0)));
        while (processed.charAt(matchingIdx + 1) == ',' || matchingIdx == 0) {
            int colonIdx = processed.indexOf(':', curIdx);
            String key = processed.substring(curIdx, colonIdx);
            key = (String) lookup(key).get();
            int idx = colonIdx + 1;
            char c = processed.charAt(idx);
            if (c == '{' || c == '[') {
                matchingIdx = match(processed, idx, counterpart(c));
                Node node = parse(processed.substring(idx, matchingIdx + 1));
                dictionary.define(key, node);
                curIdx = matchingIdx + 2;
            } else {
                int commaIdx = processed.indexOf(',', curIdx);
                boolean predicate = commaIdx > closingIdx || commaIdx == -1;
                String value = processed.substring(colonIdx + 1, predicate ? closingIdx : commaIdx);
                dictionary.define(key, lookup(value));
                if (predicate) break;
                curIdx = commaIdx + 1;
            }
        }
        return dictionary;
    }


    private static Value lookup(String key) throws JSONDecodeException {
        try {
            if (key.contains("$")) {
                String str = dict.get(Integer.valueOf(key.substring(1)));
                str = removeUTFCharacters(str).toString();
                return new Value(str);
            } else if (key.equals("null")) return new Value(null);
            else return new Value(Double.valueOf(key));
        } catch (NumberFormatException e) {
            throw new JSONDecodeException(e.getMessage());
        }
    }

    private static int match(String exp, int init, char close) {
        char start = exp.charAt(init);
        for (int i = init + 1; i < exp.length(); i++) {
            char cur = exp.charAt(i);
            if (cur == start) i = match(exp, i, close);
            else if (cur == close) return i;
        }
        return -1;
    }

    private static char counterpart(char c) {
        switch (c) {
            case '[':
                return ']';
            case '{':
                return '}';
            default:
                throw new RuntimeException();
        }
    }

    private static String process(String raw) {
        raw = raw.replaceAll("\\\\\\\\", "\\\\u005c\\\\u005c")
                .replaceAll("\\\\\"", "\\\\u0022"); // Replace special characters
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(raw);

        // Process raw JSON string
        StringBuilder processed = new StringBuilder();
        int idx = 0, prevIdx = 0, matchIdx;
        while (matcher.find()) {
            String match = matcher.group(1);
            dict.add(match);
            matchIdx = matcher.start();
            String middle = raw.substring(prevIdx, matchIdx);
            prevIdx = matchIdx + matcher.group().length();
            processed.append(middle).append("$").append(idx);
            idx++;
        }
        processed.append(raw, prevIdx, raw.length());

        return processed.toString().replaceAll("\\s+", ""); // Remove any blank space
    }

    private static StringBuffer removeUTFCharacters(String data) {
        Pattern pattern = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher matcher = pattern.matcher(data);
        StringBuffer buf = new StringBuffer(data.length());
        while (matcher.find()) {
            String ch = String.valueOf((char) Integer.parseInt(matcher.group(1), 16));
            matcher.appendReplacement(buf, Matcher.quoteReplacement(ch));
        }
        matcher.appendTail(buf);
        return buf;
    }
}
