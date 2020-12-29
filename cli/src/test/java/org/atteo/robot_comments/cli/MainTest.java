package org.atteo.robot_comments.cli;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    @Test
    public void shouldRunSpotbugs() throws CliException, IOException {
        // given
        var args = new String[] { "-o", "target/out.json", "spotbugs", "-i", "src/test/resources/multipleBugs.xml" };

        // when
        Main.execute(args);

        // then
        assertThat(Paths.get("target/out.json")).exists();
    }
}
