package control;

public class InputData {
	private String xmlPath;
	private String dataPath;

	public InputData(String xml, String data) {
		this.xmlPath = xml;
		this.dataPath = data;
	}

	public String getData() {
		return dataPath;
	}

	public String getXML() {
		return xmlPath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InputData [xmlPath=" + xmlPath + ", dataPath=" + dataPath + "]";
	}
}
