package com.health.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;

/**
 * Class responsible for loading the input data.
 * @author lizzy
 *
 */
public final class InputLoader {

	private Map<String, Table> map = new HashMap<String, Table>();
	private List<InputLoadedObserver> observerList = new ArrayList<InputLoadedObserver>();

	/**
	 * Load all the input data.
	 * @param data	input data
	 */
	public void loadTables(final List<InputData> data) {
		map.clear();

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				this.loadTable(data.get(i));
			}
		}

		notifyObservers();
	}

	private void notifyObservers() {
		for (InputLoadedObserver observer : observerList) {
			observer.inputLoaded();
		}
	}

	private void loadTable(final InputData input) {
		try {
			Table table = Input.readTable(input.getFilePath(),
					input.getConfigPath());
			map.put(input.getName(), table);

		} catch (IOException | ParserConfigurationException | SAXException
				| InputException e) {
			System.out
					.println("Error: Something went wrong parsing the config and data!");

			e.printStackTrace();

			return;
		}
	}

	/**
	 * Add an observer.
	 * @param observer	observer to add
	 */
	public void addInputLoadedObserver(final InputLoadedObserver observer) {
		if (observer != null) {
			observerList.add(observer);
		}
	}

	/**
	 * Remove an observer.
	 * @param observer	observer to remove
	 */
	public void removeInputLoadedObserver(final InputLoadedObserver observer) {
		observerList.remove(observer);
	}

	/**
	 * Get map of tables.
	 * @return map
	 */
	public Map<String, Table> getMap() {
		return map;
	}

}
