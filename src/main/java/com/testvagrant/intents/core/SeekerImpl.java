package com.testvagrant.intents.core;


import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentNotFoundException;
import gherkin.ast.ScenarioDefinition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.testvagrant.intents.predicates.ScenarioPredicate.findMatchingScenario;


public class SeekerImpl implements Seeker{

    private String intentId;
    public SeekerImpl(String intentId) {
        this.intentId = checkNotNull(intentId, "Intent ID can't be null. It should be a valid intent id.");
    }

    @Override
    public gherkin.ast.Feature seekFeature(List<gherkin.ast.Feature> features) throws FeatureNotFoundException {
        List<gherkin.ast.Feature> validFeatures = checkNotNull(features, "Features can't be null. It should be a valid list of features.");
        Stream<gherkin.ast.Feature> featureStream = validFeatures.stream();
        Optional<gherkin.ast.Feature> matchedFeature = featureStream.filter(feature -> feature.getChildren().stream().filter(findMatchingScenario(intentId)).findFirst().isPresent()).findFirst();
        if(matchedFeature.isPresent()) {
            return matchedFeature.get();
        }
        throw new FeatureNotFoundException(intentId);
    }

    @Override
    public ScenarioDefinition seekScenario(gherkin.ast.Feature feature) throws IntentNotFoundException {
        gherkin.ast.Feature validFeature = checkNotNull(feature,"Feature can't be null. It should be a valid feature object.");
        Optional<ScenarioDefinition> elements = validFeature.getChildren().stream().filter(findMatchingScenario(intentId)).findFirst();
        if(elements.isPresent()) {
            return elements.get();
        }
        throw new IntentNotFoundException(intentId);
    }
}
