package com.zurakin.codingriddles.cucumber;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    public static String readResource(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + filename)));
    }
}
