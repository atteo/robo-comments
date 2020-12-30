package org.atteo.robot_comments.cli;

import java.util.function.Supplier;

import org.atteo.robot_comments.cli.api.Command;
import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "upload", commandDescription = "Upload comments to Gerrit")
public class GerritUploadArguments implements Command {
    public GerritUploadArguments(GerritArguments gerritArguments) {

    }

    @Override
    public RobotReview filter(JCommander jCommander, Supplier<RobotReview> reviewSupplier) {
        return null;
    }
}
