package org.atteo.robot_comments.cli;

import java.util.List;
import java.util.function.Supplier;

import org.atteo.robot_comments.cli.api.Command;
import org.atteo.robot_comments.cli.api.Plugin;
import org.atteo.robot_comments.gerrit.Gerrit;
import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "gerrit", commandDescription = "Upload/Downloads comments to/from Gerrit")
public class GerritArguments implements Plugin {
    @Parameter(names = { "-g", "--gerrit-url" }, description = "URL to the Gerrit instance", required = true)
    private String gerritUrl;

    @Parameter(names = { "-u", "--user"}, description = "Gerrit user")
    private String user;

    @Parameter(names = { "-p", "--password"}, description = "Gerrit password")
    private String password;

    public String getGerritUrl() {
        return gerritUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private Gerrit gerrit;
    private GerritUploadArguments gerritUploadArguments = new GerritUploadArguments(this);

    public Gerrit getGerrit() {
        return gerrit;
    }

    @Override
    public List<Command> getSubCommands() {
        return List.of(gerritUploadArguments);
    }

    @Override
    public RobotReview filter(JCommander gerritCommands, Supplier<RobotReview> robotReviewSupplier) {
        gerrit = new Gerrit(gerritUrl, user, password);
        return null;
    }
}
