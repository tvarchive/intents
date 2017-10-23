package com.testvagrant.intents.utils;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FeatureFinder {

    private String path;

    public FeatureFinder() {
        this(System.getProperty("user.dir")+"/src/test/resources");
    }

    public FeatureFinder(String path) {
        this.path = path;
    }

    public List<gherkin.ast.Feature> findFeatures() {
        List<gherkin.ast.Feature> features = new ArrayList<>();
        List<File> files = FileFinder.fileFinder(path).find(FileExtension.FEATURE);
        files.forEach(file -> {
            try {
                gherkin.ast.Feature feature = GherkinToFeatureConverter.gherkinToFeatureConverter(file).convert();
                features.add(feature);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
        return features;
    }
}
