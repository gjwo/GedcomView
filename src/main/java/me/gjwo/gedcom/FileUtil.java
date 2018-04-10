package me.gjwo.gedcom;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil
{
    private static final String BASE_RESOURCE_PATH = "src/main/resources/";

    public static String readFile(String path) throws IOException {
        return readFile(path, Charset.defaultCharset());
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(BASE_RESOURCE_PATH + path));
        return new String(encoded, encoding);
    }

}
