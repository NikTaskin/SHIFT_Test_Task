package mergeSort.stringTransformer;

public class StringToString implements Transformer<String, String> {
    @Override
    public String transform(String data) {
        return data;
    }
}
