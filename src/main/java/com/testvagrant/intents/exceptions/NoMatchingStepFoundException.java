package com.testvagrant.intents.exceptions;


public class NoMatchingStepFoundException extends Exception {

    public NoMatchingStepFoundException(String step) {
        super(String.format("Unable to find any step definition matching step '%s'",step));
    }

}
