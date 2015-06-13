package com.health.input;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;

/**
 * Utility class for parsing input data.
 */
public final class Input {
  protected Input() {
  }

  /**
   * Given an input file and input descriptor file, parses the input file into a {@link Table}.
   *
   * @param filePath
   *          the path of the input file.
   * @param configPath
   *          the path of the input descriptor file.
   * @return a table representing the parsed input file.
   * @throws ParserConfigurationException
   *           if a DocumentBuilder cannot be created which satisfies the configuration requested.
   * @throws SAXException
   *           if any parse error occur.
   * @throws IOException
   *           if any IO errors occur.
   * @throws InputException
   *           if the input descriptor file is not formatted correctly.
   */
  public static Table readTable(final String filePath, final String configPath) throws IOException,
      ParserConfigurationException, SAXException, InputException {
    InputDescriptor config = new InputDescriptor(configPath);
    Parser parser = getParser(config.getFormat());

    if (parser == null) {
      throw new InputException(
          String.format("Unknown format '%s' specified in input descriptor '%s'.",
              config.getFormat(), configPath));
    }

    return parser.parse(filePath, config);
  }

  private static Parser getParser(final String format) {
    assert format != null;

    switch (format.toLowerCase()) {
    case "xls":
      return new XlsParser();
    case "xlsx":
      XlsParser xls = new XlsParser();
      return xls;
    case "text":
      return new TextParser();
    default:
      return null;
    }
  }
}
