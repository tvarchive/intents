package com.testvagrant.intents.utils;


public enum FileExtension {

    JAVA(".java"),
    TEXT(".txt"),
    SQL(".sql"),
    FEATURE(".feature"),
    EXCEL(".xls");

    private String fileExtension;
    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

}
