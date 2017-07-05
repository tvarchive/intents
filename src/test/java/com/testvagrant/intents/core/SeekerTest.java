package com.testvagrant.intents.core;


import com.testvagrant.intents.IntentTestBase;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;
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
    public void returnValidFeatureForAValidIntentID() throws FeatureNotFoundException {
        Seeker seeker = new SeekerImpl(getValidIntentid());
        Feature feature = seeker.seekFeature(features);
        Assert.assertEquals("","hello-world",feature.getId());
    }

    @Test(expected = FeatureNotFoundException.class)
    public void throwFeatureNotFoundExceptionWhenInvalidIntentIDIsPassed() throws FeatureNotFoundException {
        Seeker seeker = new SeekerImpl(getInvalidIntentid());
        Feature feature = seeker.seekFeature(features);
    }
}
