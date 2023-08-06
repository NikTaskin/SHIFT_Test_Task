package parser;

import java.util.List;

public class MergeSortParameters {
    private SortDataType dataType;
    private SortMode sortMode = SortMode.ASCEND;
    private String outputFileName = "out.txt";
    private List<String> inputFileNames;

    public SortDataType getDataType() {
        return dataType;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public List<String> getInputFileNames() {
        return inputFileNames;
    }

    public void setDataType(SortDataType dataType) {
        this.dataType = dataType;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setInputFileNames(List<String> inputFileNames) {
        this.inputFileNames = inputFileNames;
    }
}
