package com.testvagrant.intents.core;


import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentNotFoundException;

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
    public Feature seekFeature(List<Feature> features) throws FeatureNotFoundException {
        List<Feature> validFeatures = checkNotNull(features, "Features can't be null. It should be a valid list of features.");
        Stream<Feature> featureStream = validFeatures.stream();
        Optional<Feature> matchedFeature = featureStream.filter(feature -> feature.getElements().stream().filter(findMatchingScenario(intentId)).findFirst().isPresent()).findFirst();
        if(matchedFeature.isPresent()) {
            return matchedFeature.get();
        }
        throw new FeatureNotFoundException(intentId);
    }

    @Override
    public Elements seekScenario(Feature feature) throws IntentNotFoundException {
        Feature validFeature = checkNotNull(feature,"Feature can't be null. It should be a valid feature object.");
        Optional<Elements> elements = validFeature.getElements().stream().filter(findMatchingScenario(intentId)).findFirst();
        if(elements.isPresent()) {
            return elements.get();
        }
        throw new IntentNotFoundException(intentId);
    }
}
