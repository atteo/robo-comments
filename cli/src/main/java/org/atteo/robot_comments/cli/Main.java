package org.atteo.robot_comments.cli;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import org.atteo.classindex.ClassIndex;
import org.atteo.robot_comments.file.Mapper;
import org.atteo.robot_comments.specification.RobotReview;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        try {
            execute(args);
        } catch (CliException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ParameterException e) {
            System.err.println("Unknown parameter: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Cannot access the file: " + e.getMessage());
            System.exit(1);
        }
    }

    static void execute(String[] args) throws IOException {
        var plugins = discoverPlugins();

        var commonArguments = new CommonArguments();
        var jCommander= JCommander.newBuilder()
            .expandAtSign(false)
            .addObject(commonArguments)
            .build();

        registerRecursively(jCommander, plugins);

        jCommander.parse(args);

        if (jCommander.getParsedCommand() == null) {
            jCommander.usage();
            System.exit(1);
        }

        if (commonArguments.isHelp()) {
            jCommander.usage();
            System.exit(0);
        }

        Supplier<RobotReview> robotReviewSupplier = () -> readRobotReviewFromFile(commonArguments);

        var nestedCommander = jCommander;
        while (nestedCommander.getParsedCommand() != null) {
            nestedCommander = jCommander.getCommands().get(jCommander.getParsedCommand());
            var robotReview = ((Command) nestedCommander.getObjects().get(0)).filter(nestedCommander, robotReviewSupplier);
            robotReviewSupplier = () -> robotReview;
        }

        var robotReview = robotReviewSupplier.get();
        if (robotReview != null) {
            writeRobotReviewToFile(commonArguments, robotReview);
        }
    }

    private static void writeRobotReviewToFile(CommonArguments commonArguments, RobotReview robotReview) throws IOException {
        OutputStream outputStream;
        var outputFile = commonArguments.getOutputFile();
        if (outputFile != null) {
            try {
                outputStream = new FileOutputStream(Paths.get(outputFile).toFile());
            } catch (FileNotFoundException e) {
                throw new CliException("Output file not found: " + e.getMessage(), e);
            }
        } else {
            outputStream = System.out;
        }

        new Mapper().write(robotReview, outputStream);
    }

    private static RobotReview readRobotReviewFromFile(CommonArguments commonArguments) {
        InputStream inputStream;
        var inputFile = commonArguments.getInputFile();
        if (inputFile != null) {
            try {
                inputStream = new FileInputStream(Paths.get(inputFile).toFile());
            } catch (FileNotFoundException e) {
                throw new CliException("Output file not found: " + e.getMessage(), e);
            }
        } else {
            inputStream = System.in;
        }

        try {
            return new Mapper().read(inputStream);
        } catch (IOException e) {
            throw new CliException("Cannot read file: " + e.getMessage());
        }
    }

    private static List<? extends Plugin> discoverPlugins() {
        return StreamSupport.stream(ClassIndex.getSubclasses(Plugin.class).spliterator(), false)
            .map(klass -> {
                try {
                    return klass.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(toList());
    }

    private static void registerRecursively(JCommander parentCommander, List<? extends Command> plugins) {
        plugins.forEach(plugin -> {
            parentCommander.addCommand(plugin);
            var parametersAnnotation = plugin.getClass().getAnnotation(Parameters.class);
            var name = parametersAnnotation.commandNames()[0];
            var commander = parentCommander.getCommands().get(name);

            registerRecursively(commander, plugin.getSubCommands());
        });
    }
}
