package com.maplinking.endpoint;

public interface Validable {

    void validate() throws ValidationException;

    default void isNull(Object obj, String message) throws ValidationException {
        if (obj == null)
            throw new ValidationException(message);
    }

    default void isNullOrEmpty(String string, String message) throws ValidationException {
        if (string == null || string.trim().isEmpty())
            throw new ValidationException(message);
    }
}
