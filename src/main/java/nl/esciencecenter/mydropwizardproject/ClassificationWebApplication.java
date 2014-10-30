package nl.esciencecenter.mydropwizardproject;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.esciencecenter.mydropwizardproject.health.TemplateHealthCheck;
import nl.esciencecenter.mydropwizardproject.resources.JobOrderResource;
import nl.esciencecenter.mydropwizardproject.resources.JobStatusResource;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ClassificationWebApplication extends Application<ClassificationWebConfiguration> {
    @Inject
    private JobOrderResource jobOrderResource;
    @Inject
    private JobStatusResource jobStatusResource;
    @Inject
    private XenonManager xenonManager;

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new GuiceModule());
        ClassificationWebApplication app = injector.getInstance(ClassificationWebApplication.class);
        app.run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ClassificationWebConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(ClassificationWebConfiguration configuration, Environment environment) {
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(jobOrderResource);
        environment.jersey().register(jobStatusResource);
        environment.lifecycle().manage(xenonManager);
    }
}