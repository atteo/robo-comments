package org.atteo.robot_comments.cli;

import com.beust.jcommander.Parameter;

public class CommonArguments {
    @Parameter(names = { "-i", "--input-file"}, description = "Input file, if omitted standard input is used")
    private String inputFile;

    @Parameter(names = { "-o", "--output-file"}, description = "Output file, if omitted standard output is used")
    private String outputFile;

    @Parameter(names = { "-h", "--help"}, help = true, description = "Output file, if omitted standard output is used")
    private boolean help = false;

    public boolean isHelp() {
        return help;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getInputFile() {
        return inputFile;
    }
}
