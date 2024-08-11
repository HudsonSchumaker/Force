package br.com.schumaker.octopus.ioc;

import br.com.schumaker.octopus.io.PropertiesReader;

import java.util.Properties;

public class Environment {

    public static final String SERVER_PORT = "server.port";
    public static final String SERVER_CONTEXT = "server.context";

    private static final String SERVER_PORT_DEFAULT = "8080";
    private static final String SERVER_CONTEXT_DEFAULT = "/";
    private static final Environment INSTANCE = new Environment();
    private final Properties properties;

    private Environment() {
        properties = PropertiesReader.loadProperties();
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public String getKey(String key) {
        return properties.getProperty(key);
    }

    public Integer getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));
    }

    public String getServerContext() {
        return properties.getProperty(SERVER_CONTEXT, SERVER_CONTEXT_DEFAULT);
    }
}
