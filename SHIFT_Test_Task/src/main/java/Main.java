import mergeSort.FileMergeSorter;
import parser.InputArgsParser;
import parser.MergeSortParameters;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        InputArgsParser inputArgsParser = InputArgsParser.getInstance();
        try {
            MergeSortParameters mergeSortParameters = inputArgsParser.parseInputArgs(args);
            FileMergeSorter mergeSorter = new FileMergeSorter();
            mergeSorter.sort(mergeSortParameters);
        } catch (IllegalArgumentException | ParseException e) {
            logger.error(e.getMessage());
            inputArgsParser.printUsage();
            System.exit(1);
        }
    }
}
