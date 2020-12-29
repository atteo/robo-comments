package org.atteo.robot_comments.specification;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Writer {
    /**
     * Writes robot review data to file.
     * @param robotReview robot review to write
     * @param file destination file
     * @throws IOException thrown when file cannot be written
     */
    public static void writeToFile(RobotReview robotReview, String file) throws IOException {
        var mapper = new ObjectMapper();

        mapper.writeValue(Paths.get(file).toFile(), robotReview);
    }
}
