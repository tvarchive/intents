package com.testvagrant.intents.core;


import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.testvagrant.intents.predicates.ScenarioPredicate.findMatchingScenario;


public class SeekerImpl implements Seeker{

    private String intentId;
    public SeekerImpl(String intentId) {
        this.intentId = intentId;
    }

    @Override
    public Feature seekFeature(List<Feature> features) throws FeatureNotFoundException {
        Stream<Feature> featureStream = features.stream();
        Optional<Feature> matchedFeature = featureStream.filter(feature -> feature.getElements().stream().filter(findMatchingScenario(intentId)).findFirst().isPresent()).findFirst();
        if(matchedFeature.isPresent()) {
            return matchedFeature.get();
        }
        throw new FeatureNotFoundException(intentId);
    }

    @Override
    public Elements seekScenario(Feature feature) {
        Elements elements = feature.getElements().stream().filter(findMatchingScenario(intentId)).findFirst().get();
        return elements;
    }
}
