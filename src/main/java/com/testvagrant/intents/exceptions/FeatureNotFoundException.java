package com.testvagrant.intents.exceptions;


public class FeatureNotFoundException extends IntentException {

    public FeatureNotFoundException(String intentId) {
        super(String.format("Cannot find any feature with intent '%s'",intentId));
    }
}
