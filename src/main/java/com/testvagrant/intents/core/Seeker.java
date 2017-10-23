package com.testvagrant.intents.core;


import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentNotFoundException;
import gherkin.ast.ScenarioDefinition;

import java.util.List;

public interface Seeker {

    gherkin.ast.Feature seekFeature(List<gherkin.ast.Feature> features) throws FeatureNotFoundException;

    ScenarioDefinition seekScenario(gherkin.ast.Feature feature) throws IntentNotFoundException;

}
