
## **Hilfen von Copilot**


### **1 Jackson: the best alternative for serialization of complex schemas**
- **Best for**: Most general-purpose use cases.
- **Why**:
  - Highly flexible and widely adopted.
  - Supports advanced features like custom serializers/deserializers, polymorphic types, and annotations for fine-grained control.
  - Handles complex nested objects and collections easily.
- **Recommendation**: Use Jackson if you need a robust, feature-rich solution for serializing/deserializing JSON.

---

#### **1.1 Weitere Lösungen: Gson**
- **Best for**: Lightweight and simple use cases.
- **Why**:
  - Easy to set up and use with minimal configuration.
  - Good for straightforward JSON structures.
- **Recommendation**: Use Gson if you want a lightweight library and don't need advanced features like custom serializers.

---

#### **1.2 Weitere Lösungen: Moshi**
- **Best for**: Modern projects, especially with Kotlin or immutable objects.
- **Why**:
  - Designed for modern Java and Kotlin projects.
  - Better performance than Gson in some cases.
  - Supports immutable objects and adapters for custom serialization.
- **Recommendation**: Use Moshi if you're working in a modern Java/Kotlin environment or need better performance.

---

#### **1.3 Weitere Lösungen: org.json**
- **Best for**: Simple, manual JSON manipulation.
- **Why**:
  - Useful for quick and simple JSON parsing or creation.
  - No annotations or complex setup required.
- **Recommendation**: Use org.json if you only need basic JSON manipulation without mapping to Java objects.

---

## **2 Jackson**
---

### **2.1 Abhängigkeit von der verwendeten library**
Wenn man Jackson verwendet, folgendes berücksichtigen:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

---

### **2.2 Jackson Annotations**
Here are some common use cases for Jackson annotations

---

#### **2.2.1 Include Field**
The field is used as it is in Json.

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {

    @JsonProperty("requestId")      // Include this field
    private String requestId;
}

```

---

#### **2.2.2 Include only if not NULL**
- **Purpose**: Control when a field is included in the JSON (e.g., only if it's non-null).
- **Use Case**: If you want to exclude null or default values from the JSON output.

```java
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentCAMT060 {
    private String requestId;
    private String optionalField;

    // Getters and setters
}
```

---

#### **2.2.3 Rename/Customize Serialization/Deserialization**
You can use `@JsonProperty` to map fields to JSON properties during serialization/deserialization.
When to Use `@JsonProperty`
- When the JSON property name differs from the Java field name.
- When you need to control how fields are serialized or deserialized.
- When working with APIs that have specific JSON naming conventions.
```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentCAMT060 {
    @JsonProperty("req_id")
    private String requestId;

}
```
**JSON Example**:
```json
{
  "req_id": "12345"
}
```

---

#### **2.2.4 Include/Exclude Fields**
- Use `@JsonProperty` to include a field that might otherwise be ignored.
- Use `@JsonIgnore` to exclude a field from serialization/deserialization.

```java
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {

    @JsonProperty("includedField")  // Include field normally ignored because it's transient
    private transient String transientField;

    @JsonIgnore                     // Ignore this field
    private String internalData;
}
```

---

#### **2.2.5 Handle Read-Only or Write-Only Fields**
You can use `@JsonProperty` with `access` to make a field read-only or write-only.

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String generatedId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
```

---

#### **2.2.6 Default Values**
You can use `@JsonProperty` to specify default values during deserialization.

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {
    @JsonProperty(defaultValue = "defaultRequestId")
    private String requestId;

    }
}
```
If the `requestId` is missing in the JSON, it will default to `"defaultRequestId"`.

---

#### **2.2.7  `@JsonFormat`**
Format dates or other fields during serialization/deserialization.
- **Purpose**: Format fields like dates or enums during serialization/deserialization.
- **Use Case**: If `Example` has date fields or enums that need specific formatting.

```java
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Example {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date requestDate;

    // Getters and setters
}
```

---

#### **2.2.8  `@JsonCreator` and `@JsonProperty` (for Constructors)**
- **Purpose**: Specify how Jackson should deserialize objects when using constructors.
- **Use Case**: If `Example` has a constructor that needs to be used for deserialization.

```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Example {
    private String requestId;

    @JsonCreator
    public Example(@JsonProperty("request_id") String requestId) {
        this.requestId = requestId;
    }

    // Getter
    public String getRequestId() {
        return requestId;
    }
}
```

---

#### **2.2.9  `@JsonSetter` and `@JsonGetter`**
- **Purpose**: Customize the setter and getter methods for JSON properties.
- **Use Case**: If you want to map JSON properties to specific methods.

```java
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class DocumentCAMT060 {
    private String requestId;

    @JsonGetter("request_id")
    public String getRequestId() {
        return requestId;
    }

    @JsonSetter("request_id")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
```
---

#### **2.2.10 `@JsonIgnoreProperties`**
- **Purpose**: Ignore unknown properties during deserialization.
- **Use Case**: If the JSON contains fields that are not mapped to the class.

```java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentCAMT060 {
    private String requestId;

    // Getters and setters
}
```

---

#### **2.2.11 `@JsonAlias`**
- **Purpose**: Allow multiple JSON property names to map to the same field.
- **Use Case**: If the JSON uses different names for the same field in different contexts.

```java
import com.fasterxml.jackson.annotation.JsonAlias;

public class DocumentCAMT060 {
    @JsonAlias({"req_id", "requestId"})
    private String requestId;

    // Getters and setters
}
```

---
### **2.3 Example Usage**
Here’s how you can serialize and deserialize the `AppHdr` class:
```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Create an AppHdr object
        AppHdr appHdr = new AppHdr();
        appHdr.setBizMsgIdr("12345");
        appHdr.setMsgDefIdr("ABC");
        appHdr.setBizSvc("Service");
        appHdr.setCreDt("2025-04-28T12:00:00");

        // Serialize to JSON
        String json = mapper.writeValueAsString(appHdr);
        System.out.println("Serialized JSON: " + json);

        // Deserialize from JSON
        String inputJson = "{\"Fr\":null,\"To\":null,\"BizMsgIdr\":\"12345\",\"MsgDefIdr\":\"ABC\",\"BizSvc\":\"Service\",\"CreDt\":\"2025-04-28T12:00:00\"}";
        AppHdr deserializedAppHdr = mapper.readValue(inputJson, AppHdr.class);
        System.out.println("Deserialized BizMsgIdr: " + deserializedAppHdr.getBizMsgIdr());
    }
}
```

---
