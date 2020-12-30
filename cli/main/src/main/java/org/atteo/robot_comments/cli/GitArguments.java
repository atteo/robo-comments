package org.atteo.robot_comments.cli;

import java.util.List;
import java.util.function.Supplier;

import org.atteo.robot_comments.cli.api.Command;
import org.atteo.robot_comments.cli.api.Plugin;
import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

import static java.util.Collections.emptyList;

@Parameters(commandNames = "git", commandDescription = "Filter comments using data from Git")
public class GitArguments implements Plugin {
    @Override
    public RobotReview filter(JCommander jCommander, Supplier<RobotReview> review) {
        return review.get();
    }

    @Override
    public List<Command> getSubCommands() {
        return emptyList();
    }
}
