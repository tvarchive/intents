package com.testvagrant.intents.core;


import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;

import java.util.List;

public interface Seeker {

    Feature seekFeature(List<Feature> features) throws FeatureNotFoundException;

    Elements seekScenario(Feature feature);

}
