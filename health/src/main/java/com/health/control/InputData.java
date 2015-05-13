package control;

public class InputData {
	private String xmlPath;
	private String dataPath;
	
	public InputData() {
		this.xmlPath = null;
		this.dataPath = null;
	}

	public InputData(String xml, String data) {
		this.xmlPath = xml;
		this.dataPath = data;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getDataPath() {
		return dataPath;
	}



	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InputData [xmlPath=" + xmlPath + ", dataPath=" + dataPath + "]";
	}
}
