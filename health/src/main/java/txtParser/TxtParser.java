package txtParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TxtParser {

	public void readStream() {
		Path path = Paths.get("c:/temp", "data.txt");

		try (Stream<String> lines = Files.lines(path)) {
			
		//delimit lines, parse into creatObject
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
