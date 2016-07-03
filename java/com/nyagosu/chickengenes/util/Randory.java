package com.nyagosu.chickengenes.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Randory {
	
	public ArrayList<int[]> rates = new ArrayList<int[]>();
	public ArrayList<Integer> values = new ArrayList<Integer>();
	
	public Randory(){
		
	}
	
	public void addRate(int min, int max, int count){
		int[] i = {min,max,count};
		rates.add(i);
	}
	
	public int getValue(){
		for(int[] rate : rates){
			int min = rate[0];
			int max = rate[1];
			int cnt = rate[2];
			
			for (int i = min; i < max; i++)
				this.values.add((int)(Math.random()*(max-min))+min);
		   
		}
		return this.values.get((int) (Math.random()*this.values.size()));
	}
	
	public void reset(){
		this.resetRates();
		this.resetValues();
	}
	
	public void resetRates(){
		rates = new ArrayList<int[]>();
	}
	
	public void resetValues(){
		values = new ArrayList<Integer>();
	}
	
}