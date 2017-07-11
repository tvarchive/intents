package com.testvagrant.intents.exceptions;


public class IntentNotFoundException extends IntentException {

    public IntentNotFoundException(String intentId) {
        super(String.format("Cannot find an intent `%s`. Is the feature valid?",intentId));
    }
}
