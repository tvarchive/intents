package com.testvagrant.intents.predicates;


import gherkin.ast.ScenarioDefinition;

import java.util.function.Predicate;


public class ScenarioPredicate {

    public static Predicate<ScenarioDefinition> findMatchingScenario(String intentId) {
        return elements -> elements.getName().trim().equalsIgnoreCase(intentId);
    }
}
