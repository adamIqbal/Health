package com.health.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

public class InputLoaderModule {

	private List<InputData> data;
	private static Map<String,Table> mapInstance;
	private Map<String,Table> map;
	
	public InputLoaderModule(List<InputData> d) {
		Objects.requireNonNull(d, "Argument d must not be null");
		data = d;
		map = new HashMap<String,Table>();
		if(mapInstance == null){
			mapInstance = map;
		}
	}
	
    public void loadAllData() {
        if (this.data != null) {
            for (int i = 0; i < this.data.size(); i++) {
                this.loadData(this.data.get(i));
            }
        }
    }

    private void loadData(final InputData input) {
        try {
            Table table = Input.readTable(input.getFilePath(), input.getConfigPath());
            map.put(input.getName(),table);
            
        } catch (IOException | ParserConfigurationException
                | SAXException | InputException e) {
            System.out.println("Error: Something went wrong parsing the config and data!");

            e.printStackTrace();

            return;
        }
    }
    
    public static Map<String,Table> getTableMap(){
    	return mapInstance;
    }
	
}
