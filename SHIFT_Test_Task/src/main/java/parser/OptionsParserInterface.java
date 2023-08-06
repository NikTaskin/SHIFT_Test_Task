package parser;

import org.apache.commons.cli.ParseException;

public interface OptionsParserInterface {
    MergeSortParameters parseInputArgs(String[] args) throws ParseException, IllegalArgumentException;
    void printUsage();
}
