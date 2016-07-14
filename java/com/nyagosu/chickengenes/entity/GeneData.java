package com.nyagosu.chickengenes.entity;

import java.util.Random;
import com.nyagosu.chickengenes.util.Randory;

public class GeneData {
	
	public int sex 			= 0;
	public int generation   = 0;
	public int mate_flag	= 0;
	public int maxhealth 	= 0;
	public int attack 		= 0;
	public int defense 		= 0;
	public int eggspeed 	= 0;
	public int efficiency 	= 0;
	public int growspeed 	= 0;
	public int movespeed 	= 0;
	
	public GeneData(){
		this.setRandomValue();
	}
	
	public void setRandomValue(){
		Random r = new Random();
		this.sex = r.nextInt(2);
		
		this.mate_flag = 0;
		this.generation = 0;
		
		//plus
		Randory rand = new Randory();
		rand.addRate(1,45);
		rand.addRate(2,20);
		rand.addRate(3,15);
		rand.addRate(4,10);
		rand.addRate(5,5);
		rand.addRate(6,3);
		rand.addRate(7,2);
		
		int plus_factor_num = rand.getValue();
		for(int i = 0;i<plus_factor_num;i++){
			switch(r.nextInt(7)){
				case 0:this.maxhealth++;	break;
				case 1:this.attack++;		break;
				case 2:this.defense++;		break;
				case 3:this.eggspeed++;		break;
				case 4:this.efficiency++;	break;
				case 5:this.growspeed++;	break;
				case 6:this.movespeed++;	break;
			};
		}
		rand.resetValues();
		
		//minus
		rand.addRate(0, 70);
		rand.addRate(1, 20);
		rand.addRate(2, 10);
		rand.addRate(3, 5);
		rand.addRate(4, 3);
		rand.addRate(5, 2);
		int minus_factor_num = rand.getValue();
		for(int ii = 0;ii<minus_factor_num;ii++){
			switch(r.nextInt(7)){
				case 0:this.maxhealth--;	break;
				case 1:this.attack--;		break;
				case 2:this.defense--;		break;
				case 3:this.eggspeed--;		break;
				case 4:this.efficiency--;	break;
				case 5:this.growspeed--;	break;
				case 6:this.movespeed--;	break;
			};
		}
		rand.resetValues();
	}
	
	public GeneData(
			int sex,
			int generation,
			int mate_flag,
			int maxhealth,
			int attack,
			int defense,
			int eggspeed,
			int stamina,
			int growspeed,
			int movespeed
			){
		this.sex = sex;
		this.generation = generation;
		this.mate_flag = mate_flag;
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
		if(datas.length == 10){
			this.sex = Integer.parseInt(datas[0]);
			this.generation = Integer.parseInt(datas[1]);
			this.mate_flag = Integer.parseInt(datas[2]);
			this.maxhealth = Integer.parseInt(datas[3]);
			this.attack = Integer.parseInt(datas[4]);
			this.defense = Integer.parseInt(datas[5]);
			this.eggspeed = Integer.parseInt(datas[6]);
			this.efficiency = Integer.parseInt(datas[7]);
			this.growspeed = Integer.parseInt(datas[8]);
			this.movespeed = Integer.parseInt(datas[9]);
		}else{
			this.setRandomValue();
		}
	}
	
	public String getDataString(){
		String data_string = "";
		data_string += String.valueOf(this.sex) + ",";
		data_string += String.valueOf(this.generation) + ",";
		data_string += String.valueOf(this.mate_flag) + ",";
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
		str += "generation:" + String.valueOf(this.generation);
		str += " mate_flag:" + String.valueOf(this.mate_flag);
		str += " maxhealth:" + String.valueOf(this.maxhealth);
		str += " attack:" + String.valueOf(this.attack);
		str += " defense:" + String.valueOf(this.defense);
		str += " eggspeed:" + String.valueOf(this.eggspeed);
		str += " efficiency:" + String.valueOf(this.efficiency);
		str += " growspeed:" + String.valueOf(this.growspeed);
		str += " movespeed:" + String.valueOf(this.movespeed);
		return str;
	}
	
	public GeneData mix(GeneData gene){
		Random r = new Random();
		return new GeneData(
					r.nextInt(2),
					Math.max(this.generation, gene.generation) + 1,
					0,
					this.maxhealth + gene.maxhealth,
					this.attack + gene.attack,
					this.defense + gene.defense,
					this.eggspeed + gene.eggspeed,
					this.efficiency + gene.efficiency,
					this.growspeed + gene.growspeed,
					this.movespeed + gene.movespeed
				);
	}
	
	public GeneData addGene(GeneData gene){
		Random r = new Random();
		return new GeneData(
					this.sex,
					this.generation,
					this.mate_flag,
					this.maxhealth + gene.maxhealth,
					this.attack + gene.attack,
					this.defense + gene.defense,
					this.eggspeed + gene.eggspeed,
					this.efficiency + gene.efficiency,
					this.growspeed + gene.growspeed,
					this.movespeed + gene.movespeed
				);
	}
	
	public void randomSexValue(){
		Random r = new Random();
		this.sex = r.nextInt(2);
	}
	
}
