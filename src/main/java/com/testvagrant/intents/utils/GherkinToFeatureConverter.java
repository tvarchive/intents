package com.testvagrant.intents.utils;


import cucumber.util.FixJava;
import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.TokenMatcher;
import gherkin.ast.GherkinDocument;

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

    public gherkin.ast.Feature convert() throws IOException {
        String gherkin = FixJava.readReader(new InputStreamReader(
                new FileInputStream(feature.getPath()), "UTF-8"));
        TokenMatcher matcher = new TokenMatcher();
        Parser<GherkinDocument> parser = new Parser(new AstBuilder());
        GherkinDocument parse = parser.parse(gherkin, matcher);
        return parse.getFeature();
    }
}
