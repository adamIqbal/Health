package com.health.visuals;


public class Tuple{
	
	public final String from;
	public final String to;
	
	public Tuple(String f, String t){
		this.from = f;
		this.to = t;
	}

	public void print() {
		System.out.println(this.from + " / " + this.to);
		
	}
	
	public boolean equals(Object that){
		if(that instanceof Tuple){
			if(((Tuple) that).from.equals(this.from))
		}
	}

}