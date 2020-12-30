package org.atteo.robot_comments.grep;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.atteo.robot_comments.cli.api.CliException;
import org.atteo.robot_comments.cli.api.Plugin;
import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "grep", commandDescription = "Filters comments which contain given pattern")
public class Grep implements Plugin {
    @Parameter(description = "regular expression to filter by", required = true)
    private String pattern;

    @Override
    public RobotReview filter(JCommander jCommander, Supplier<RobotReview> review) throws CliException {
        var robotReview = review.get();

        var compiledPattern = Pattern.compile(pattern);

        var comments = robotReview.getComments()
            .stream()
            .filter(comment -> compiledPattern.matcher(comment.getMessage()).find()
                || compiledPattern.matcher(comment.getPath()).find())
            .collect(Collectors.toList());

        return new RobotReview(comments);
    }
}
