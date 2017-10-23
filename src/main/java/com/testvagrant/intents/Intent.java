package com.testvagrant.intents;


import com.testvagrant.intents.builders.MethodExecutorBuilder;
import com.testvagrant.intents.core.MethodExecutor;
import com.testvagrant.intents.core.Seeker;
import com.testvagrant.intents.core.SeekerImpl;
import com.testvagrant.intents.exceptions.IntentException;
import com.testvagrant.intents.exceptions.NoMatchingStepFoundException;
import com.testvagrant.intents.utils.FeatureFinder;
import cucumber.api.DataTable;
import gherkin.ast.ScenarioDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * An Intent is a small reusable action, similar to a scenario. However it has no purpose, if it isn't used in a context.
 * <br>
 * <b>Eg:</b>
 * <pre>
 *     <code>
 *         {@literal @}Intent
 *          <b>Scenario:</b> Login
 *          <b>Given</b> User enters username as JohnNash
 *          <b>And</b> User enters password as NashJohn
 *          <b>When</b> User clicks on submit
 *          <b>Then</b> User is navigated to HomeScreen
 *     </code>
 * </pre>
 *<br>
 * <b>Usage:</b>
 * <pre>
 *     <code>
 *         <b>Scenario:</b> Book a cab
 *         <b>Given </b> <b>Intent:</b> Login
 *         <b>When</b>  I enter to and from locations
 *         <b>And</b>   I click on BookACab
 *         <b>Then</b>  I should receive a booking confirmation
 *     </code>
 * </pre>
 *
 *  Sometimes it is needed to override default values as in the above example user would like to login other than 'JohnNash'.
 *
 *  It is possible to modify the default values of Intent as below.
 * <b>Usage:</b>
 * <pre>
 *     <code>
 *         <b>Scenario:</b> Book a cab
 *         <b>Given </b>  <b>DataIntent:</b> Login
 *         |Admin|pass@123|
 *         <b>When</b> I enter to and from locations
 *         <b>And</b>  I click on BookACab
 *         <b>Then</b> I should receive a booking confirmation
 *     </code>
 * </pre>
 *
 * <b>Using intent in code:</b>
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         intentRunner(intentId).run();
 *     </code>
 * </pre>
 *
 * <br>
 * If the default data is overridden, pass the datatable object to intent runner.
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         intentRunner(intentId).useDatatable(datatable).run();
 *     </code>
 * </pre>
 * <br>
 * If the step definitions are created in a package apart form the standard <b>steps</b> package, you can let Intent know about it as below.
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         String stepDefinitionsPackage = "com.example.exampleStepsPackage";
 *         intentRunner(intentId)
 *         .useDatatable(datatable)
 *         .locateStepDefinitionsAt(stepDefinitionsPackage)
 *         .run();
 *     </code>
 * </pre>
 */

public class Intent {

    private String intentId;
    private Optional<DataTable> dataTable;
    private Optional<String> stepDefinitionPackage;
    private static List<gherkin.ast.Feature> features = new ArrayList<>();
    private Optional<String> jarStepDefinitionPackage;

    public Intent() {
        dataTable = Optional.empty();
        stepDefinitionPackage = Optional.empty();
        jarStepDefinitionPackage = Optional.empty();
        findFeatures();

    }

    public Intent(String stepDefsPackage){
        stepDefinitionPackage = Optional.of(stepDefsPackage);
    }

    public Intent useDatatable(DataTable datatable) {
        dataTable = Optional.of(datatable);
        return this;
    }

    public Intent locateStepDefinitionsAt(String packageName) {
        stepDefinitionPackage = Optional.of(packageName);
        return this;
    }

    public Intent locateJarStepDefinitionsAt(String packageName) {
        jarStepDefinitionPackage = Optional.of(packageName);
        return this;
    }

    public void run(String intentId) throws IntentException {
        Seeker seeker = new SeekerImpl(intentId);
        gherkin.ast.Feature feature = seeker.seekFeature(features);
        ScenarioDefinition elements = seeker.seekScenario(feature);
        MethodExecutor executor = new MethodExecutorBuilder()
                .withDataTable(dataTable)
                .withJarStepDefinitionPackageName(jarStepDefinitionPackage)
                .withStepDefinationPackageName(stepDefinitionPackage)
                .build();
        executor.findPatterns();
        elements.getSteps().forEach(step -> {
            try {
                executor.exec(step.getText());
            } catch (NoMatchingStepFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
    }


    private void findFeatures() {
        if(features.size()==0) {
            features = new FeatureFinder().findFeatures();
        }
    }
}
