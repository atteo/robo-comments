package org.atteo.robot_comments.cli.api;

import java.util.List;
import java.util.function.Supplier;

import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;

import static java.util.Collections.emptyList;

public interface Command {
    default List<Command> getSubCommands() {
        return emptyList();
    }

    RobotReview filter(JCommander jCommander, Supplier<RobotReview> review);
}
