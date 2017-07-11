package com.testvagrant.intents.core;


import com.sun.istack.internal.NotNull;
import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentNotFoundException;

import javax.annotation.Nonnull;
import java.util.List;

public interface Seeker {

    Feature seekFeature(@NotNull List<Feature> features) throws FeatureNotFoundException;

    Elements seekScenario(@Nonnull Feature feature) throws IntentNotFoundException;

}
