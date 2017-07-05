package com.testvagrant.intents.exceptions;


public class FeatureNotFoundException extends Exception {

    public FeatureNotFoundException(String intentId) {
        super(String.format("Cannot find any feature with intent '%s'",intentId));
    }
}
