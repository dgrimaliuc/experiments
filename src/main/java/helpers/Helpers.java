package helpers;


import com.google.gson.Gson;
import io.cucumber.java.Scenario;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.reducers.ReducingMethod;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static helpers.PropertiesReader.getProperty;
import static java.lang.Long.parseLong;


public class Helpers {
    public static ArrayList<Object> stepResults = null;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Helpers::generateCucumberReport));
    }

    public static <T> T createInstance(Class<?> clPath) {
        WebDriver driverInstance = null;
        try {
            Class<?> clazz = Class.forName(clPath.getName());
            var constructor = clazz.getDeclaredConstructor(WebDriver.class, Logger.class, WebDriverWait.class);
            var logger = Logger.getLogger(clazz);
            driverInstance = DriverProvider.createDriver();
            var waitor = new WebDriverWait(driverInstance, parseLong(getProperty("timeout")));
            return (T) constructor.newInstance(driverInstance, logger, waitor);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Objects.requireNonNull(driverInstance).close();
            throw new RuntimeException(e);
        }

    }


    public static void generateCucumberReport() {
        if (Files.exists(Path.of("target/cucumber-html-reports/cucumber.json"))) {
            File reportOutputDirectory = new File("target/");
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add("target/cucumber-html-reports/cucumber.json");
            String buildNumber = "v1";
            String projectName = "Tekwill Automation";

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
            configuration.setBuildNumber(buildNumber);
            configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
            configuration.addReducingMethod(ReducingMethod.HIDE_EMPTY_HOOKS);
            // additional metadata presented on main page
            configuration.addClassifications("Platform", "Windows");
            configuration.addClassifications("Browser", "Chrome");
            configuration.addClassifications("Branch", "release/1.0");

            // optionally add metadata presented on main page via properties file
            List<String> classificationFiles = new ArrayList<>();
            configuration.addClassificationFiles(classificationFiles);
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
        } else
            System.out.println("Json file was not found");
    }


    public static <T> Logger createLogger(Class<T> clazz) {
        return Logger.getLogger(clazz);

    }

    public static class Appender extends WriterAppender {
        private final Scenario scenario;

        public Appender(Scenario scenario) {
            this.scenario = scenario;
        }

        @Override
        public void doAppend(LoggingEvent event) {
            scenario.log(event.getRenderedMessage());
        }
    }

    public static <T> T getValue(String format, Class<T> resultType) {
        Gson gson = new Gson();
        var arg = gson.toJson(stepResults)
                .replaceAll("true", "True")
                .replaceAll("false", "False");

        PythonExecutor pe = new PythonExecutor(gson.toJson(format) + ".format(*" + arg + ")");
        pe.execute();
        return gson.fromJson("\"" + pe.getResult() + "\"", resultType);
    }


}
