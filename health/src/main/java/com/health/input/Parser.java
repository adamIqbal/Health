package com.health.input;

import java.io.IOException;

import com.health.Table;

/**
 * Interface for a parser of an input file.
 */
public interface Parser {
    /**
     * Given a path to an input file and an input descriptor, parses the input
     * file into a {@link Table}.
     *
     * @param path
     *            the path of the input file.
     * @param config
     *            the input descriptor.
     * @return a table representing the parsed input file.
     * @throws InputException
     *             if any input errors occur.
     * @throws IOException
     *             if any IO errors occur.
     */
    Table parse(String path, InputDescriptor config) throws InputException,
            IOException;
}
