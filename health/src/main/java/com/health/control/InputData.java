package com.health.control;

/**
 * Represents a 3-tuple consisting of the file path, configuration path and
 * table name.
 */
public final class InputData {
    private final String configPath;
    private final String filePath;
    private final String name;

    /**
     * Creates a new instance of {@link InputData} with the given file path,
     * config path and name.
     *
     * @param filePath
     *            the path to the data file.
     * @param configPath
     *            the path to the configuration file.
     * @param name
     *            the name of the table.
     */
    public InputData(final String filePath, final String configPath, final String name) {
        this.filePath = filePath;
        this.configPath = configPath;
        this.name = name;
    }

    /**
     * Gets the path to the data file.
     *
     * @return the path to the data file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the path to the configuration file.
     *
     * @return the path to the configuration file.
     */
    public String getConfigPath() {
        return configPath;
    }

    /**
     * Gets the name of the table.
     *
     * @return the name of the table.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "InputData [filePath=" + filePath + ", configPath=" + configPath + ", name=" + name + "]";
    }
}
