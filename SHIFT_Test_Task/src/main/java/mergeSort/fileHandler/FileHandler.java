package mergeSort.fileHandler;

import mergeSort.exception.DataTransformErrorException;
import mergeSort.stringTransformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class FileHandler <T> {
    private final static Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private boolean reachedEOF = false;
    private long curLineNumber = 0;
    private final String fileName;
    private final BufferedReader inputStream;
    private final Transformer<String, T> dataTransformer;
    private final Comparator<T> comparator;
    private T prevElem = null;
    private T curElem;

    public String getFileName() {
        return fileName;
    }

    public T getPrevElem() {
        return prevElem;
    }

    public T getCurElem() {
        return curElem;
    }

    public boolean isReachedEOF() {
        return reachedEOF;
    }

    public long getCurLineNumber() {
        return curLineNumber;
    }

    public FileHandler(String fileName, Transformer<String, T> dataTransformer, Comparator<T> comparator) throws IOException {
        this.inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        this.fileName = fileName;
        this.dataTransformer = dataTransformer;
        this.comparator = comparator;
        this.curElem = getNextElemFromFile();
        while (this.curElem == null) {
            if (reachedEOF) {
                break;
            }
            this.curElem = getNextElemFromFile();
        }
    }

    private T getNextElemFromFile() throws IOException {
        if (reachedEOF) {
            return null;
        }

        String nextLine = inputStream.readLine();
        ++curLineNumber;

        if (nextLine == null) {
            inputStream.close();
            prevElem = curElem;
            curElem = null;
            reachedEOF = true;
            return null;
        }

        if (nextLine.isEmpty()) {
            logger.warn("Найдена пустая строка в файле " + fileName + " в строке " + curLineNumber + ". Строка была прошущена");
            return null;
        }

        if (nextLine.contains(" ")) {
            logger.warn("Найден пробел в файле " + fileName + " в строке " + curLineNumber + ". Строка была прошущена");
            return null;
        }

        T value = null;
        try {
            value = dataTransformer.transform(nextLine);
        } catch (DataTransformErrorException e) {
            logger.error(e.getMessage() + " в файле " + fileName + " в сроке " + curLineNumber);
            return null;
        }

        return value;
    }

    private T findNextNotNullElem() throws IOException {
        T nextElem = getNextElemFromFile();
        while (nextElem == null) {
            if (reachedEOF) {
                return null;
            }
            nextElem = getNextElemFromFile();
        }
        return nextElem;
    }

    public T nextElem() throws IOException {
        T nextElem = findNextNotNullElem();
        if (nextElem == null) {
            return null;
        }

        while (comparator.compare(curElem, nextElem) < 0 && nextElem != null) {
            logger.error("Файл " + fileName + " не отсортирован в строке " + curLineNumber + ". Строка была прошущена");
            nextElem = findNextNotNullElem();
        }
        if (nextElem == null) {
            return null;
        }

        prevElem = curElem;
        curElem = nextElem;
        return curElem;
    }
}