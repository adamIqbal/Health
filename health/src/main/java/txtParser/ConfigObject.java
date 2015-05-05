package txtParser;

import java.util.ArrayList;

public class ConfigObject {

	public ArrayList<String> columns;

	public void columns(ArrayList<String> columns) {
		this.columns = columns;

	}

	public ArrayList<String> getString() {
		return columns;
	}

}
