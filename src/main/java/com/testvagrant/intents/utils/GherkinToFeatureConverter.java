package com.testvagrant.intents.utils;


import com.google.gson.Gson;
import com.testvagrant.intents.entities.Feature;
import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class GherkinToFeatureConverter {

    private File feature;
    private GherkinToFeatureConverter(File feature) {
        this.feature = feature;
    }

    public static GherkinToFeatureConverter gherkinToFeatureConverter(File feature) {
        return new GherkinToFeatureConverter(feature);
    }

    public Feature convert() throws IOException {
        String gherkin = FixJava.readReader(new InputStreamReader(
                new FileInputStream(feature.getPath()), "UTF-8"));
        Gson gson = new Gson();
        StringBuilder json = new StringBuilder();
        JSONFormatter formatter = new JSONFormatter(json);
        Parser parser = new Parser(formatter);
        parser.parse(gherkin,feature.getPath(),0);
        formatter.done();
        formatter.close();
        String val = json.toString().replaceFirst("\\[","").substring(0,json.toString().length()-2);
        return gson.fromJson(val, Feature.class);
    }
}
