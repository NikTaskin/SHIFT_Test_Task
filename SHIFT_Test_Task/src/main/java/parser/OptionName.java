package parser;

public enum OptionName {
    OUTPUT_FILENAME("o"),
    SORT_MODE_ASCEND("a"),
    SORT_MODE_DESCEND("d"),
    SORT_DATA_TYPE_STRING("s"),
    SORT_DATA_TYPE_INTEGER("i"),
    HELP("h");

    private final String optionName;

    public String getOptionName() {
        return optionName;
    }

    OptionName(String name) {
        this.optionName = name;
    }
}
