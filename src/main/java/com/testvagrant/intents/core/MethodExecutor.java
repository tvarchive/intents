package com.testvagrant.intents.core;


import com.testvagrant.intents.Intent;
import com.testvagrant.intents.exceptions.NoMatchingStepFoundException;
import cucumber.api.java.en.*;
import io.cucumber.datatable.DataTable;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * In Cucumber every step definition method can be identified by a unique pattern.
 * MethodExecutor reflects into all the classes with Cucumber annotations
 * and identifies a matching method for the step in an {@link Intent}
 * </p>
 */
public class MethodExecutor {

    private static Map<String, Method> patterns;
    private static boolean patternFound;
    private Reflections reflections;
    private Matcher matcher;
    private Optional<DataTable> dataTable;
    private Optional<String> packageName;
    private Optional<String> optionalStepDefinitionPackage;

    public MethodExecutor() {
        patterns = new HashMap<>();
        dataTable = Optional.empty();
        packageName = Optional.empty();
        optionalStepDefinitionPackage = Optional.empty();
    }

    public void setDataTable(Optional<DataTable> dataTable) {
        this.dataTable = dataTable;
    }

    public void setPackageName(Optional<String> packageName) {
        this.packageName = packageName;
    }

    public void setOptionalStepDefinitionPackage(Optional<String> optionalStepDefinitionPackage) {
        this.optionalStepDefinitionPackage = optionalStepDefinitionPackage;
    }

    public void exec(String name) throws NoMatchingStepFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println("Executing step "+name);
        Method method = getMethod(name);
        List<String> args = getData();
        try {
            if(method.getParameterCount()>0) {
                method.invoke(method.getDeclaringClass().newInstance(), args.toArray());
            }
            else {
                method.invoke(method.getDeclaringClass().newInstance(),args);
            }
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    public synchronized MethodExecutor findPatterns() {
        if(patterns.size()==0) {
            Set<String> packageNames = getPackageNames();
            packageNames.forEach(packageName -> {
                reflections = new Reflections(packageName, new MethodAnnotationsScanner());
                Set<Method> allCucumberMethods = getAnnotatedMethods();
                allCucumberMethods.forEach(method -> {
                    Arrays.stream(method.getDeclaredAnnotations()).forEach(annotation -> {
                        readPattern(method, annotation);
                    });
                });
            });
        }
        return this;
    }

    private Set<Method> getAnnotatedMethods() {
        Set<Method> allCucumberMethods = new HashSet<>();
        allCucumberMethods = reflections.getMethodsAnnotatedWith(Given.class);
        allCucumberMethods.addAll(reflections.getMethodsAnnotatedWith(When.class));
        allCucumberMethods.addAll(reflections.getMethodsAnnotatedWith(Then.class));
        allCucumberMethods.addAll(reflections.getMethodsAnnotatedWith(And.class));
        allCucumberMethods.addAll(reflections.getMethodsAnnotatedWith(But.class));
        return allCucumberMethods;
    }

    public synchronized void ifNotFound() {
        if(patterns.size()>0) {
            patternFound = true;
        }
    }

    private void readPattern(Method method, Annotation annotation) {
        if (annotation instanceof Given) {
            patterns.put(((Given) annotation).value(), method);
        } else if (annotation instanceof When) {
            patterns.put(((When) annotation).value(), method);
        } else if (annotation instanceof Then) {
            patterns.put(((Then) annotation).value(), method);
        } else if (annotation instanceof And) {
            patterns.put(((And) annotation).value(), method);
        } else if (annotation instanceof But) {
            patterns.put(((But) annotation).value(), method);
        }
    }


    private Method getMethod(String name) throws NoMatchingStepFoundException {
        String pattern = findMatchingPattern(name);
        Method method = patterns.get(pattern);
        return method;
    }

    private String findMatchingPattern(String name) throws NoMatchingStepFoundException {
        Optional<String> first = patterns.keySet().stream().filter(pattern -> {
            Pattern pattern1 = Pattern.compile(pattern);
            matcher = pattern1.matcher(name);
            return matcher.matches();
        }).findFirst();
        if(first.isPresent()) {
            return first.get();
        }
        throw new NoMatchingStepFoundException(name);
    }



    private List<String> getData() {
        List<String> args = new ArrayList<>();
        if(dataTable.isPresent()) {
            Map<Object, Object> datatableMap = dataTable.get().asMaps(String.class, String.class).get(0);
                for (int index = 1; index <= matcher.groupCount(); index++) {
                    if(datatableMap.containsKey(matcher.group(index))) {
                        args.add(String.valueOf(datatableMap.get(matcher.group(index))));
                    } else {
                        args.add(matcher.group(index));
                    }
                }
        } else {
            for (int groupIndex = 1; groupIndex <= matcher.groupCount(); groupIndex++) {
                args.add(matcher.group(groupIndex));
            }
        }
        return args;
    }


    private String getPackageName() {
       return packageName.orElse("steps");
    }

    private Set<String> getPackageNames() {
        Set<String> packageNames = new HashSet<>();
        packageNames.add(packageName.orElse("steps"));
        packageNames.add(optionalStepDefinitionPackage.orElse("steps"));
        return packageNames;
    }
}
