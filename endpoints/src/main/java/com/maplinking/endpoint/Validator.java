package com.maplinking.endpoint;


import java.util.Collection;

public class Validator {

    private static final Validator INSTANCE = new Validator();

    private Validator() {

    }

    public static Validator getInstance() {
        return INSTANCE;
    }

    void validate(Validable... validableObject) throws ValidationException {
        for (Validable object : validableObject)
            object.validate();
    }

    void validate(Collection<? extends Validable> validableCollection) throws ValidationException {
        for (Validable object : validableCollection)
            object.validate();
    }
}
