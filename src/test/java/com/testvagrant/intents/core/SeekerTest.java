package com.testvagrant.intents.core;


import com.testvagrant.intents.IntentTestBase;
import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentException;
import com.testvagrant.intents.exceptions.IntentNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.when;

public class SeekerTest extends IntentTestBase {

    @Before
    public void setup() {
        when(features.stream()).thenReturn(getFeatures().stream());
    }

    @Test
    public void returnValidFeatureForAValidIntentID() throws IntentException {
        Seeker seeker = new SeekerImpl(getValidIntentid());
        Feature feature = seeker.seekFeature(features);
        Assert.assertEquals("Invalid feature ID","hello-world",feature.getId());
    }

    @Test(expected = FeatureNotFoundException.class)
    public void throwFeatureNotFoundExceptionWhenInvalidIntentIDIsPassed() throws IntentException {
        Seeker seeker = new SeekerImpl(getInvalidIntentid());
        seeker.seekFeature(features);
    }

    @Test
    public void returnValidIntentForAValidIntentID() throws IntentException {
        Seeker seeker = new SeekerImpl(getValidIntentid());
        Elements elements = seeker.seekScenario(getFeature());
        Assert.assertEquals("Invalid element ID","element-id",elements.getId());
    }

    @Test(expected = IntentNotFoundException.class)
    public void throwIntentNotFoundExceptionWhenInvalidIntentIDIsPassed() throws IntentException {
        Seeker seeker = new SeekerImpl(getInvalidIntentid());
        seeker.seekScenario(getFeature());
    }

    @Test(expected = NullPointerException.class)
    public void cannotInstantiateASeekerWithNull() throws FeatureNotFoundException {
        Seeker seeker  = new SeekerImpl(null);
    }

    @Test(expected = NullPointerException.class)
    public void cannotseekFeaturesFromANull() throws FeatureNotFoundException {
        Seeker seeker  = new SeekerImpl(getValidIntentid());
        seeker.seekFeature(null);
    }

    @Test(expected = NullPointerException.class)
    public void cannotseekScenarioFromANull() throws IntentNotFoundException {
        Seeker seeker  = new SeekerImpl(getValidIntentid());
        seeker.seekScenario(null);
    }

}
