package work.funcke;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import work.funcke.configuration.ApplicationConfiguration;
import work.funcke.configuration.CDIResourceConfiguration;
import work.funcke.filter.AuthorizationFilter;
import work.funcke.rest.ExampleResource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Entry point for WebBackend
 * @author jonas.funcke <jonas.funcke@dynatrace.com>
 */
public class Main {
    /**
     * Starts and initialized embedded Jetty.
     * @param args String[] - args passed to the program from the commandline
     * @throws Exception - thrown if errors occur during startup.
     */
    public static void main(String[] args) throws Exception {
        ApplicationConfiguration appConfig = new ApplicationConfiguration();
        BlockingQueue<Runnable> threadPoolQueue = new BlockingArrayQueue<>(20, 2, 80);
        QueuedThreadPool threadPool = new QueuedThreadPool(20, 2, (int) TimeUnit.MINUTES.toMillis(1), threadPoolQueue);        // default idle time as defined in the QueuedThreadPool
        threadPool.setName("jetty-test-server");
        threadPool.setDaemon(true);

        Server server = new Server(threadPool);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(appConfig.getInteger("http-port"));
        server.setConnectors(new Connector[]{connector});

        /**
         * Handler for REST-Resources
         */
        final ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");

        /**
         * Handler for static files including the frontend app and its assets
         */
        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(Main.class.getClassLoader().getResource("FrontendResources").toString());
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        servletContextHandler.addFilter(registerCorsFilter(), "/*", EnumSet.of(DispatcherType.REQUEST));

        final ServletHolder servletHolder = new ServletHolder(new ServletContainer(registerResources()));
        servletContextHandler.addServlet(servletHolder, "/*");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resourceHandler, servletContextHandler, new DefaultHandler()});
        server.setHandler(handlers);

        server.start();
        server.join();
    }

    private static FilterHolder registerCorsFilter() {
        FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedMethods", "GET, POST, PUT, DELETE, OPTIONS");
        filterHolder.setInitParameter("allowedHeaders", "x-requested-with, Content-Type, origin, authorization, accept, client-security-token");
        return filterHolder;
    }

    private static ResourceConfig registerResources() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(ExampleResource.class);
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(AuthorizationFilter.class);
        resourceConfig.register(new ApplicationResourceBinder(new CDIResourceConfiguration()));
        return resourceConfig;
    }
}
