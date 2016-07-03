package com.nyagosu.chickengenes.entity;

import java.util.Random;

import com.nyagosu.chickengenes.util.Randory;

public class GeneData {
	
	public int sex;
	public int maxhealth;
	public int attack;
	public int defense;
	public int eggspeed;
	public int efficiency;
	public int growspeed;
	public int movespeed;
	
	public GeneData(){
		
		Random r = new Random();
		this.sex = r.nextInt(2);
        
		Randory rand = new Randory();
		rand.addRate(200,250,60);
		rand.addRate(251,280,25);
		rand.addRate(281,290,10);
		rand.addRate(291,300,4);
		rand.addRate(301,320,1);
		
		this.maxhealth = rand.getValue();
		rand.resetValues();
		
		this.attack = rand.getValue();
		rand.resetValues();
		
		this.defense = rand.getValue();
		rand.resetValues();
		
		this.eggspeed = rand.getValue();
		rand.resetValues();
		
		this.efficiency = rand.getValue();
		rand.resetValues();
		
		this.growspeed = rand.getValue();
		rand.resetValues();
		
		this.movespeed = rand.getValue();
		rand.resetValues();
	}
	
	public GeneData(
			int sex,
			int maxhealth,
			int attack,
			int defense,
			int eggspeed,
			int stamina,
			int growspeed,
			int movespeed
			){
		this.sex = sex;
		this.maxhealth = maxhealth;
		this.attack = attack;
		this.defense = defense;
		this.eggspeed = eggspeed;
		this.efficiency = stamina;
		this.growspeed = growspeed;
		this.movespeed = movespeed;
	}
	
	public GeneData(String gene_data_string){
		String[] datas = gene_data_string.split(",");
		this.sex = Integer.parseInt(datas[0]);
		this.maxhealth = Integer.parseInt(datas[1]);
		this.attack = Integer.parseInt(datas[2]);
		this.defense = Integer.parseInt(datas[3]);
		this.eggspeed = Integer.parseInt(datas[4]);
		this.efficiency = Integer.parseInt(datas[5]);
		this.growspeed = Integer.parseInt(datas[6]);
		this.movespeed = Integer.parseInt(datas[7]);
	}
	
	public String getDataString(){
		String data_string = "";
		data_string += String.valueOf(this.sex) + ",";
		data_string += String.valueOf(this.maxhealth) + ",";
		data_string += String.valueOf(this.attack) + ",";
		data_string += String.valueOf(this.defense) + ",";
		data_string += String.valueOf(this.eggspeed) + ",";
		data_string += String.valueOf(this.efficiency) + ",";
		data_string += String.valueOf(this.growspeed) + ",";
		data_string += String.valueOf(this.movespeed);
    	return data_string;
	}
	
	public String getDataString4Debug(){
		String str = "";
		str += "sex:" + String.valueOf(this.sex);
		str += " maxhealth:" + String.valueOf(this.maxhealth);
		str += " attack:" + String.valueOf(this.attack);
		str += " defense:" + String.valueOf(this.defense);
		str += " eggspeed:" + String.valueOf(this.eggspeed);
		str += " efficiency:" + String.valueOf(this.efficiency);
		str += " growspeed:" + String.valueOf(this.growspeed);
		str += " movespeed:" + String.valueOf(this.movespeed);
		return str;
	}
	
}
