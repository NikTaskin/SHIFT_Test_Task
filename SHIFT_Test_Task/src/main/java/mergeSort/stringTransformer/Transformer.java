package mergeSort.stringTransformer;

import mergeSort.exception.DataTransformErrorException;

public interface Transformer <I, O> {
    O transform(I data) throws DataTransformErrorException;
}