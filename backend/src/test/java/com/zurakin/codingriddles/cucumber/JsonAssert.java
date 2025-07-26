package com.zurakin.codingriddles.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;

public class JsonAssert {
    public static void assertJsonEquals(String actual, String expected) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedNode = mapper.readTree(expected);
        JsonNode actualNode = mapper.readTree(actual);
        assertJsonEquals(actualNode, expectedNode);
    }

    private static void assertJsonEquals(JsonNode actualNode, JsonNode expectedNode) {
        if (expectedNode.isObject()) {
            Iterator<String> fieldNames = expectedNode.fieldNames();
            while (fieldNames.hasNext()) {
                String field = fieldNames.next();
                JsonNode expectedValue = expectedNode.get(field);
                if (expectedValue.isTextual() && "$JSON_IGNORE".equals(expectedValue.asText())) {
                    // Only check that the field exists in actualNode
                    if (!actualNode.has(field)) {
                        throw new AssertionError("Expected field '" + field + "' to be present in actual JSON, but it was missing.\nFull actual JSON: " + actualNode.toPrettyString());
                    }
                } else {
                    // Compare value
                    if (!actualNode.has(field) || !actualNode.get(field).equals(expectedValue)) {
                        throw new AssertionError("Field '" + field + "' does not match.\nExpected: " + expectedValue + "\nActual: " + actualNode.get(field) + "\nFull actual JSON: " + actualNode.toPrettyString());
                    }
                }
            }
        } else if (expectedNode.isArray()) {
            if (!actualNode.isArray() || actualNode.size() != expectedNode.size()) {
                throw new AssertionError("Array size does not match.\nExpected: " + expectedNode.size() + "\nActual: " + actualNode.size() + "\nFull actual JSON: " + actualNode.toPrettyString());
            }
            for (int i = 0; i < expectedNode.size(); i++) {
                JsonNode expectedElement = expectedNode.get(i);
                JsonNode actualElement = actualNode.get(i);
                if (expectedElement.isTextual() && "$JSON_IGNORE".equals(expectedElement.asText())) {
                    // Ignore this element in comparison
                    continue;
                }
                // Recursively compare elements
                assertJsonEquals(actualElement, expectedElement);
            }
        } else {
            // For non-object and non-array nodes, compare directly
            if (!actualNode.equals(expectedNode)) {
                throw new AssertionError("JSON does not match.\nExpected: " + expectedNode + "\nActual: " + actualNode + "\nFull actual JSON: " + actualNode.toPrettyString());
            }
        }
    }
}
