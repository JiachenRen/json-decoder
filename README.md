# JSON Decoder
A super light weight JSON library that handles JSON serialization and deserialization. 
## Usage
### Deserialization
```java
String json = "[1,2,null,false,{\"key1\":\"value\",\"key2\":[]},true]"
try {
  Node node = JSONDecoder.decode(json);
  println(node.encode());
  // Produces JSON array [1,2,null,false,{"key":"value","key2":[]},true]
} catch (JSONDecodeError e) {
  e.printStackTrace();
}
```
### Serialization
```java
Dictionary dictionary = new Dictionary();
dictionary.define("participant", new Value("P03"));
List list = new List();
list.add(new Value(1));
list.add(new Value("abc"));
list.add(new Value(null));
dictionary.define("sequence", list);
dictionary.define("timestamp", new Value(new Date()));
String json = dictionary.encode();
```
