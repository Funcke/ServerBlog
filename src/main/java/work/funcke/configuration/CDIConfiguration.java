package work.funcke.configuration;

import java.util.Map;

public interface CDIConfiguration {
    public Map<String, String> entities();
    public String get(String key);
}
