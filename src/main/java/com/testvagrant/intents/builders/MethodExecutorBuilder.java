package com.testvagrant.intents.builders;

import com.testvagrant.intents.core.MethodExecutor;
import cucumber.api.DataTable;

import java.util.Optional;

/**
 * Created by krishnanand on 05/07/17.
 */
public class MethodExecutorBuilder {

    private MethodExecutor methodExecutor;

    public MethodExecutorBuilder() {
        methodExecutor = new MethodExecutor();
    }

    public MethodExecutorBuilder withDataTable(Optional<DataTable> dataTable) {
        methodExecutor.setDataTable(dataTable);
        return this;
    }

    public MethodExecutorBuilder withStepDefinationPackageName(Optional<String> packageName) {
        methodExecutor.setPackageName(packageName);
        return this;
    }

    public MethodExecutorBuilder withJarStepDefinitionPackageName(Optional<String> packageName) {
        methodExecutor.setOptionalStepDefinitionPackage(packageName);
        return this;
    }

    public MethodExecutor build() {
        return methodExecutor;
    }
}
