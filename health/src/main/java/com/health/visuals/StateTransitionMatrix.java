package com.health.visuals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;

/**
 * Creates a State Transition Matrix.
 * @author Lizzy Scholten
 *
 */
public final class StateTransitionMatrix {
	
	private StateTransitionMatrix() {
		// Does nothing
	}
	
	public static void createStateTrans(EventList eList, List<EventSequence> seqList){
		
		ArrayList<String> eArr = new ArrayList<String>();
		
		for(Event e: eList.getList()){
			if(eArr.contains(e.getCode())){
			
			}
			else {
				eArr.add(e.getCode());
			}
		}
		
		for(String s: eArr){
			System.out.println(s);
		}	
		
		String[][] matrix = new String[eArr.size()+1][eArr.size()+1];
		
		matrix[0][0] = "Event types";
		
		for(int k = 1; k < eArr.size()+1; k++){
			matrix[0][k] = eArr.get(k-1);
			matrix[k][0] = eArr.get(k-1);
		}
		
		System.out.println(Arrays.deepToString(matrix));
			
		
		//B A A
		//A B
		//A B A
		for(EventSequence eSeq: seqList){
			String[] codePat = eSeq.getCodePattern();
			
			for(int c = 1; c < codePat.length; c++){
				String from = codePat[c-1];
				String to = codePat[c];
				
				for(int a = 1; a < matrix[0].length; a++){
					boolean found1 = false;
					
					System.out.println("matrix a0 : "+ matrix[a][0]);
					System.out.println("from : "+ from);
					
					if(matrix[a][0].equals(from)){
						for(int b = 1; b < matrix[1].length; b++){
							System.out.println("matrix 0b : "+ matrix[0][b]);
							System.out.println("to : "+ to);
							if(matrix[0][b].equals(to)){
								if (matrix[a][b] == null) {
									matrix[a][b] = "1";
								}
								else {
									int val = Integer.parseInt(matrix[a][b]);
									val = val + 1;
									matrix[a][b] = Integer.toString(val);
								}
								found1 = true;
								break;
							}		
						}
					}
					
					if(found1){
						break;
					}
				}
			}
			
			
		}	
		
		System.out.println(Arrays.deepToString(matrix));
	
	}	
}