# Super Light-Weight JSON Decoder
A super light weight JSON library that handles JSON serialization and deserialization. 
## Usage
```java
String json = "{\"key\":\"value\"}"
Node node = JSONDecoder.decode();
println(node.encode());
// Produces {"key":"value"}
```