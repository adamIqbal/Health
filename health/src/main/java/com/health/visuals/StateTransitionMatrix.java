package com.health.visuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a State Transition Matrix.
 * @author Lizzy Scholten
 *
 */
public final class StateTransitionMatrix {
	
	private StateTransitionMatrix(){
		// Does nothing
	}
	
	public static void main(String[] args){
		
		List<EventConnection> conList = new ArrayList<EventConnection>();
		List<String> typ = new ArrayList<String>();
		
		Event a = new Event("A", null, null);
		Event b = new Event("B", null, null);
		Event c = new Event("C", null, null);
		
		typ.add(a.codeName);
		typ.add(b.codeName);
		typ.add(c.codeName);
		
		EventConnection con1 = new EventConnection(a,c);
		EventConnection con2 = new EventConnection(a,b);
		EventConnection con3 = new EventConnection(b,c);
		EventConnection con4 = new EventConnection(a,c);
		
		conList.add(con1);
		conList.add(con2);
		conList.add(con3);
		conList.add(con4);
		
		createStateTrans(conList, typ);
	}
	
	
	public static void createStateTrans(List<EventConnection> conList, List<String> types){
		
		HashMap<EventConnection, Integer> map = createMap(conList);
		
		for (Map.Entry<EventConnection, Integer> entry: map.entrySet()){
			//entry.getKey().print();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
		
	}
	
	public static HashMap<EventConnection, Integer> createMap(List<EventConnection> conList){
		
		HashMap<EventConnection, Integer> map = new HashMap<EventConnection, Integer>();
		
		for (EventConnection eC: conList){
			if(map.containsKey(eC)){
				int val = map.get(eC);
				val = val + 1;
				map.put(eC, val);
			}
			else{
				map.put(eC, 1);
			}
		}
		return map;
	}
	
}