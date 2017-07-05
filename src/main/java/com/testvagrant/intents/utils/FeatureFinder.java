package com.testvagrant.intents.utils;


import com.testvagrant.intents.entities.Feature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.testvagrant.intents.utils.FileFinder.fileFinder;


public class FeatureFinder {

    private String path;

    public FeatureFinder() {
        this(System.getProperty("user.dir")+"/src/test/resources");
    }

    public FeatureFinder(String path) {
        this.path = path;
    }

    public List<Feature> findFeatures() {
        List<Feature> features = new ArrayList<>();
        List<File> files = FileFinder.fileFinder(path).find(FileExtension.FEATURE);
        files.forEach(file -> {
            try {
                Feature feature = GherkinToFeatureConverter.gherkinToFeatureConverter(file).convert();
                features.add(feature);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
        return features;
    }
}
