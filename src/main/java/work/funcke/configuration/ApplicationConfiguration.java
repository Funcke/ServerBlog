package work.funcke.configuration;

/**
 * Wrapper around application.properties.
 *
 * This class aims to provide access to configurations stored in
 * "application.properties".
 * Please note that this structure is <b>read only</b>.
 *
 * If changes to the application.properties are necessary, please change
 * the properties file in shut-down state of the application as changes
 * may affect components in the overall software.
 *
 * @author Jonas Funcke <jonas@funcke.work>
 */
public class ApplicationConfiguration extends PropertyConfiguration implements Singleton<ApplicationConfiguration> {
    /**
     * Identifier for application.properties.
     * Port for standard HTTP requests.
     *
     * {@value}
     */
    public static String HTTP_PORT = "http-port";
    /**
     * Identifier for application.properties.
     * The size of the job queue used to feed the application server.
     *
     * {@value}
     */
    public static String JOB_QUEUE_CAPACITY = "server-thread-pool-queue-capacity";
    /**
     * Identifier for application.properties.
     * Grow rate of the job queue used to feed the application server thread pool.
     *
     * {@value}
     */
    public static String JOB_QUEUE_GROW = "server-thread-pool-queue-grow";
    /**
     * Identifier for application.properties
     * Maximum capacity of the job queue used to feed the application server thread pool.
     *
     * {@value}
     */
    public static String JOB_QUEUE_MAX_CAPACITY = "server-thread-pool-queue-max-capacity";
    /**
     * Identifier for application.properties
     * Maximum thread count for server thread pool.
     *
     * {@value}
     */
    public static String THREAD_POOL_MAX_SIZE = "server-thread-pool-max-threads";
    /**
     * Identifier for application.properties
     * Minimum thread count for thread pool.
     *
     * {@value}
     */
    public static String THREAD_POOL_MIN_SIZE = "server-thread-pool-min-threads";
    /**
     * Identifier for application.properties
     * Maximum IDLE time until an unused thread gets disposed from the thread pool.
     *
     * {@value}
     */
    public static String THREAD_POOL_IDLE = "server-thread-pool-max-idle";
    /**
     * Identifier for application.properties
     * Identifier for the thread pool.
     *
     * {@value}
     */
    public static String THREAD_POOL_NAME = "server-thread-pool-name";

    /**
     * Accessible instance. This instance will be made available to all users
     * of this class.
     */
    private static volatile ApplicationConfiguration instance;
    /**
     * Mutex object to synchronize instance initialization.
     */
    private static Object mutex = new Object();

    /**
     * Instance access method.
     * This method manages access to the central instance available.
     * If the instance is being accessed the first time, this method will
     * create a new instance.
     *
     * @return ApplicationConfiguration - Global Application configuration instance
     */
    public static ApplicationConfiguration getInstance() {
        ApplicationConfiguration result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new ApplicationConfiguration();
            }
        }
        return result;
    }

    /**
     * c'tor
     */
    protected ApplicationConfiguration() {
        super("application");
    }
}
