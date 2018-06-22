# Super Light-Weight JSON Decoder
A super light weight JSON library that handles JSON serialization and deserialization. 
## Usage
```java
String json = "[1,2,null,false,{\"key1\":\"value\",\"key2\":[]},true]"
try {
  Node node = JSONDecoder.decode();
  println(node.encode());
  // Produces JSON array [1,2,null,false,{"key":"value","key2":[]},true]
} catch (JSONDecodeError e) {
  e.printStackTrace();
}
```
