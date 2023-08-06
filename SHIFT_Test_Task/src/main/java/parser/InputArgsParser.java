package parser;

import org.apache.commons.cli.*;

public class InputArgsParser implements OptionsParserInterface {
    private static InputArgsParser inputArgsParser;
    private final Options options;

    private InputArgsParser() {
        options = new Options();
        options.addOption(OptionName.SORT_MODE_ASCEND.getOptionName(), false, "Режим сортировки по возрастанию [по умолчанию - по возрастанию]");
        options.addOption(OptionName.SORT_MODE_DESCEND.getOptionName(), false, "Режим сортировки по убыванию [по умолчанию - по возрастанию]");
        options.addOption(OptionName.SORT_DATA_TYPE_STRING.getOptionName(), false, "Задает тип данных строковой сортировки");
        options.addOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName(), false, "Задает целочисленный тип данных сортировки");
        options.addOption(OptionName.OUTPUT_FILENAME.getOptionName(), true, "Задает имя выходному файлу [по умолчанию: out.txt]");
    }

    public static InputArgsParser getInstance() {
        if (inputArgsParser == null) {
            synchronized (InputArgsParser.class) {
                if (inputArgsParser == null) {
                    inputArgsParser = new InputArgsParser();
                }
            }
        }
        return inputArgsParser;
    }

    private MergeSortParameters buildSortAttributes(CommandLine parsedOptions) {
        MergeSortParameters mergeSortParameters = new MergeSortParameters();

        if (parsedOptions.hasOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName())) {
            mergeSortParameters.setDataType(SortDataType.INTEGER);
        } else {
            mergeSortParameters.setDataType(SortDataType.STRING);
        }

        if (parsedOptions.hasOption(OptionName.SORT_MODE_DESCEND.getOptionName())) {
            mergeSortParameters.setSortMode(SortMode.DESCEND);
        } else {
            mergeSortParameters.setSortMode(SortMode.ASCEND);
        }

        if (parsedOptions.hasOption(OptionName.OUTPUT_FILENAME.getOptionName())) {
            mergeSortParameters.setOutputFileName(parsedOptions.getOptionValue(OptionName.OUTPUT_FILENAME.getOptionName()));
        } else {
            mergeSortParameters.setOutputFileName("out.txt");
        }

        mergeSortParameters.setInputFileNames(parsedOptions.getArgList());

        return mergeSortParameters;
    }
    
    private void validateParsedOptions(CommandLine parsedOptions) throws IllegalArgumentException {
        if (parsedOptions.hasOption(OptionName.SORT_MODE_ASCEND.getOptionName()) && parsedOptions.hasOption(OptionName.SORT_MODE_DESCEND.getOptionName())) {
            throw new IllegalArgumentException("Выберете один из двух режимов сортировки (по умолчанию: по возрастанию)");
        }

        if (parsedOptions.hasOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName()) == parsedOptions.hasOption(OptionName.SORT_DATA_TYPE_STRING.getOptionName())) {
            throw new IllegalArgumentException("Выберите один из двух типов сортировки (Числовой или строковый)");
        }

        if (parsedOptions.getArgs().length < 1) {
            throw new IllegalArgumentException("Нет файлов для сортировки");
        }
    }

    @Override
    public MergeSortParameters parseInputArgs(String[] args) throws ParseException, IllegalArgumentException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine parsedOptions = commandLineParser.parse(options, args);
        if (parsedOptions.hasOption(OptionName.HELP.getOptionName())) {
            return null;
        }
        validateParsedOptions(parsedOptions);
        return buildSortAttributes(parsedOptions);
    }

    @Override
    public void printUsage() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Справка", options);
    }
}
