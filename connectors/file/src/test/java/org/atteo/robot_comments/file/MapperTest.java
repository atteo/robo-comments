package org.atteo.robot_comments.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.specification.Side;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {
    @Test
    public void shouldWriteToFile() throws IOException {
        // given
        var comment = new Comment("main.java", null, 5, "Missing parentheses", Side.REVISION);
        var comments = new RobotReview(List.of(comment));

        // when
        new Mapper().write(comments, "target/test.json");

        // then
        assertThat(Path.of("target/test.json")).exists();
    }

    @Test
    public void shouldReadFromFile() throws IOException {
        // when
        var robotReview = new Mapper().read("src/test/resources/test.json");

        // then
        assertThat(robotReview).isNotNull();
    }
}
