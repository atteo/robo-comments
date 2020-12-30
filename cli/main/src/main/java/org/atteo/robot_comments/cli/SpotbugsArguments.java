package org.atteo.robot_comments.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;

import org.atteo.robot_comments.cli.api.CliException;
import org.atteo.robot_comments.cli.api.Plugin;
import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.spotbugs.SpotBugsParser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "spotbugs", commandDescription = "Imports SpotBugs issues")
public class SpotbugsArguments implements Plugin {
    @Parameter(names = { "-i", "--input-file" }, description = "Input file")
    private String inputFile = "target/spotbugsXml.xml";

    @Override
    public RobotReview filter(JCommander jCommander, Supplier<RobotReview> review) throws CliException {
        try {
            return new SpotBugsParser().parse(inputFile);
        } catch (FileNotFoundException e) {
            throw new CliException("Source file not found");
        } catch (IOException e) {
            throw new CliException("Cannot read Spotbugs file");
        }
    }
}
