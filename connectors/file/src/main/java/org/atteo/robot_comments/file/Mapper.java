package org.atteo.robot_comments.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.atteo.robot_comments.specification.RobotReview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Mapper {
    private final ObjectMapper mapper;

    public Mapper() {
        mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Writes robot review data to file.
     * @param robotReview robot review to write
     * @param stream destination stream
     * @throws IOException thrown when file cannot be written
     */
    public void write(RobotReview robotReview, OutputStream stream) throws IOException {
        mapper.writeValue(stream, robotReview);
    }

    /**
     * Writes robot review data to file.
     * @param robotReview robot review to write
     * @param file destination file
     * @throws IOException thrown when file cannot be written
     */
    public void write(RobotReview robotReview, String file) throws IOException {
        mapper.writeValue(Paths.get(file).toFile(), robotReview);
    }

    public RobotReview read(InputStream inputStream) throws IOException {
        return mapper.readValue(inputStream, RobotReview.class);
    }

    public RobotReview read(String file) throws IOException {
        return mapper.readValue(Paths.get(file).toFile(), RobotReview.class);
    }
}
