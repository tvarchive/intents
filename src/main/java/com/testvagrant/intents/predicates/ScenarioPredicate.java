package com.testvagrant.intents.predicates;


import com.testvagrant.intents.entities.Elements;

import java.util.function.Predicate;


public class ScenarioPredicate {

    public static Predicate<Elements> findMatchingScenario(String intentId) {
        return elements -> elements.getName().trim().equalsIgnoreCase(intentId);
    }
}
