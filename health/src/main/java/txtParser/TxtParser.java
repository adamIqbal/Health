package txtParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TxtParser {

  public void readStream() {
    Path path = Paths.get("c:/temp", "data.txt");

    try (Stream<String> lines = Files.lines(path)) {
      String str = path.toString();
      String[] strs = str.split("\\|+");
      for (String s : strs) {
        System.out.println("Split..: " + s);
      }

      Pattern pattern = Pattern.compile("[^|]+");
      Matcher matcher = pattern.matcher(str);
      while (matcher.find()) {
        System.out.println("Pattern:" + matcher.group());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
