package org.atteo.robot_comments.specification;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WriterTest {
    @Test
    public void shouldWriteToFile() throws IOException {
        // given
        var comments = new RobotReview(List.of(new Comment("main.java", 5, "Missing parentheses")));

        // when
        Writer.writeToFile(comments, "test.json");

        // then
        assertThat(Path.of("test.json")).exists();
    }
}
