package mergeSort.stringTransformer;

import mergeSort.exception.DataTransformErrorException;

public class StringToInteger implements Transformer <String, Integer> {
    @Override
    public Integer transform(String data) throws DataTransformErrorException {
        Integer value = null;
        try {
            value = Integer.valueOf(data);
        } catch (NumberFormatException e) {
            throw new DataTransformErrorException("Строка не может быть преобразована в integer: " + e.getMessage());
        }
        return value;
    }
}
