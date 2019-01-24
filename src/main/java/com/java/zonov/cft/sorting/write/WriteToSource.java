package com.java.zonov.cft.sorting.write;

import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WriteToSource {

    public static <T> void write(String fileName, List<T> list, Boolean desc) {

        File output = new File(fileName);
        try (FileWriterWithEncoding fw = new FileWriterWithEncoding(output, StandardCharsets.UTF_8)) {

            if (desc) {
                Collections.reverse(list);
            }

            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                fw.write(iterator.next().toString());
                fw.append("\n");
            }

        } catch (IOException e) {
            System.err.println("Something wrong with file: " + fileName);
            e.printStackTrace();
            System.exit(2);
        }

    }
}
