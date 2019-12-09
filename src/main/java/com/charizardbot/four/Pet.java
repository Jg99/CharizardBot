package com.charizardbot.four;
import java.text.DecimalFormat;
public class Pet {
	private double strength, intellect, agility, will, power; //values for the calculations
	private String stats = ""; //final built string part 1
	private String continued = ""; // part 2
	private DecimalFormat df = new DecimalFormat("#.##"); //format to 2 decimals
	/**
	 * Wizard101 petstats calculator
	 * @param strength
	 * @param intellect
	 * @param agility
	 * @param will
	 * @param power
	 */
	public Pet(double strength, double intellect, double agility, double will, double power)
	{
		//setup the pet object
		this.strength = strength;
		this.intellect = intellect;
		this.agility = agility;
		this.will = will;
		this.power = power;
	}
	public String calculate()
	{
		//formulas
		if (strength > 500 || intellect > 500 || agility > 500 || will > 500 || power > 500)
		{
			return("invalid stat values.");
		} else {
		double dealer = (((2 * strength) + (2 * will) + (power)) * 0.0075);
		double giver = ((((2 * strength) + (2 * will) + (power)) / 200));
		double painbringer = ((((2 * strength) + (2 * will) + (power)) / 400));
		double proof = ((((2 * strength) + (2 * agility) + (power)) / 125));
		double defy = ((((2 * strength) + (2 * agility) + (power)) / 250));
		double ward = (((2 * strength) + (2 * agility) + (power)) * 0.012);
		double critstriker = ((2 * agility) + (2 * will) + power) * 0.024;
		double crithitter = ((2 * agility) + (2 * will) + power) * 0.018;
		double schoolassailant = ((2 * agility) + (2 * will) + power) / 40;
		double schoolstriker = ((2 * agility) + (2 *  will) +  power) * 0.02;
		double defender = ((2 *  intellect) + (2 *  will) +  power) * 0.024;
		double blocker =  ((2 *  intellect) + (2 *  will) +  power) * 0.02;
		double sniper = (((2 * intellect) + (2 * agility) + (power)) * 0.0075);
		double shot = (((2 *  intellect) + (2 *  agility) + (power)) / 200);
		double eye = (((2 *  intellect) + (2 *  agility) + ( power)) / 400);
		double breaker = ((((2 *  strength) + (2 *  agility) + ( power)) / 400));
		double piercer = ((((2 *  strength) + (2 *  agility) + ( power)) * 0.0015));
		double stunresist = ((((2 *  strength) + (2 *  intellect) + ( power)) / 250));
		double stunrecal = ((((2 *  strength) + (2 *  intellect) + ( power)) / 125));
		double lively = (((2 *  intellect) + (2 *  agility) + ( power)) * 0.0065);
		double healer = ((((2 *  strength) + (2 *  will) + ( power)) * 0.003));
		double medic = ((((2 *  strength) + (2 *  will) + ( power)) * 0.0065));
		double healthy = (((2 *  intellect) + (2 *  agility) + ( power)) * 0.003);
		double hBounty = (((2 * agility) + (2 * will) + power) * 0.12);
		double hGift = ((2 * agility) + (2 * will) + power) * 0.1;
		double hBoost = ((2 * agility) + (2 * will) + power) * 0.08;
		double addHealth = ((2 * agility) + (2 * will) + power) * 0.06;
		//string builder	
   		stats += "Dealer: " + (df.format(dealer)) + "\nGiver: " + (df.format(giver));
		stats += "\nPainbringer: " + df.format(painbringer) + "\nProof: " + df.format(proof);
		stats += "\nDefy: " + df.format(defy) + "\nWard: " + df.format(ward);
		stats += "\nCritical Striker: " + df.format(critstriker) + "\nCritical Hitter: " + df.format(crithitter);
		stats += "\nSchool Assailant: " + df.format(schoolassailant) + "\nSchool Striker: " + df.format(schoolstriker);
		stats += "\nDefender: " + df.format(defender) + "\nBlocker: " + df.format(blocker);
		stats += "\nSniper: " + df.format(sniper) + "\nAny School Shot: " + df.format(shot);
		continued +=  "Sharp Eye: " + df.format(eye) + "\nArmor Breaker: " + df.format(breaker);
		continued += "\nArmor Piercer: " + df.format(piercer) + "\nStun Resistant: " + df.format(stunresist);
		continued += "\nStun Recalcitrant: " + df.format(stunrecal) + "\nLively: " + df.format(lively);
		continued += "\nHealer: " + df.format(healer) + "\nMedic: " + df.format(medic) + "\nHealthy: " + df.format(healthy);
		continued += "\nHealth Bounty: " + df.format(hBounty) + "\nHealth Gift: " + df.format(hGift);
		continued += "\nHealth Boost: " + df.format(hBoost) + "\nAdd Health: " + df.format(addHealth);
		return stats;
	}
}		
		public String continuedStats ()
		{
			return continued;
		}
		public String toString()
		{
			return(this.calculate() + "\n" + this.continuedStats());
		}
}