package org.atteo.robot_comments.spotbugs;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpotBugsParserTest {
    @Test
    public void shouldParseOneBug() throws IOException {
        // given
        var stream = SpotBugsParserTest.class.getResourceAsStream("/oneBug.xml");

        // when
        var robotReview = new SpotBugsParser().parse(stream);

        // then
        assertThat(robotReview).isNotNull();
        assertThat(robotReview.getComments()).hasSize(1);

        var comment = robotReview.getComments().get(0);
        assertThat(comment.getLine()).isEqualTo(240);
        assertThat(comment.getFile()).isEqualTo("src/main/java/org/atteo/classindex/processor/ClassIndexProcessor.java");
        assertThat(comment.getMessage()).isEqualTo("Found reliance on default encoding in" +
            " org.atteo.classindex.processor.ClassIndexProcessor.readOldIndexFile(Set, String):" +
            " new java.io.FileReader(String)");
    }

    @Test
    public void shouldParseNoBugs() throws IOException {
        // given
        var stream = SpotBugsParserTest.class.getResourceAsStream("/noBugs.xml");

        // when
        var robotReview = new SpotBugsParser().parse(stream);

        // then
        assertThat(robotReview).isNotNull();
        assertThat(robotReview.getComments()).hasSize(0);
    }

    @Test
    public void shouldParseMultipleBugs() throws IOException {
        // given
        var stream = SpotBugsParserTest.class.getResourceAsStream("/multipleBugs.xml");

        // when
        var robotReview = new SpotBugsParser().parse(stream);

        // then
        assertThat(robotReview).isNotNull();
        assertThat(robotReview.getComments()).hasSize(4);
    }
}
