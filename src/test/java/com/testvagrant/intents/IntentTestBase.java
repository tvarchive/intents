package com.testvagrant.intents;

import com.testvagrant.intents.entities.Elements;
import com.testvagrant.intents.entities.Feature;
import com.testvagrant.intents.entities.Steps;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


public class IntentTestBase {

    protected ArrayList features = mock(ArrayList.class);
    private static final String VALID_INTENTID = "Element Intent ID";
    private static final String INVALID_INTENTID = "Invalid Intent ID";

    protected List<Feature> getFeatures() {
        Feature feature = getFeature();
        List<Feature> featuresList = new ArrayList<>();
        featuresList.add(feature);
        return featuresList;
    }


    protected Feature getFeature() {
        Feature feature = new Feature();
        feature.setElements(getElements());
        feature.setKeyword("Feature");
        feature.setLine(0);
        feature.setName("Hello World");
        feature.setId("hello-world");
        feature.setDescription("This is a helloworld");
        feature.setUri("/Users/krishnanand/Development/TestVagrant/optimus-eco-system/StepDefs/src/test/resources/features/A.feature");
        return feature;
    }

    private List<Elements> getElements() {
        Elements elements = new Elements();
        elements.setName(VALID_INTENTID);
        elements.setLine(1);
        elements.setId("element-id");
        elements.setDescription("This is element description");
        elements.setKeyword("Scenario");
        elements.setType("scenario");
        elements.setSteps(getSteps());
        List<Elements> elementsList = new ArrayList<>();
        elementsList.add(elements);
        return elementsList;
    }


    private List<Steps> getSteps() {
        Steps steps = new Steps();
        steps.setKeyword("Given");
        steps.setLine(2);
        steps.setName("This is a given step");
        List<Steps> stepsList = new ArrayList<>();
        return stepsList;
    }

    protected String getValidIntentid() {
        return VALID_INTENTID;
    }

    protected String getInvalidIntentid() {
        return INVALID_INTENTID;
    }
}
