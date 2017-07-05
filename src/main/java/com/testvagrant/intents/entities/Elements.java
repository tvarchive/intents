package com.testvagrant.intents.entities;

import java.util.List;

public class Elements {

    private List<Examples> examples;
    private int line;
    private String name;
    private String description;
    private String id;
    private String type;
    private String keyword;
    private List<Steps> steps;

    public List<Examples> getExamples() {
        return examples;
    }

    public void setExamples(List<Examples> examples) {
        this.examples = examples;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }
}
