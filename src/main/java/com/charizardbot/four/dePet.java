package com.charizardbot.four;
import java.text.DecimalFormat;
// PET STATS TRANSLATED INTO GERMAN, THanks to Victoria#4139 from Spiralwander community for translation
public class dePet {
	private double strength, intellect, agility, will, power; //values for the calculations
	private String stats = ""; //final built string part 1
	private String continued = ""; // part 2
	private DecimalFormat df = new DecimalFormat("#.##"); //format to 2 decimals
	public dePet(double strength, double intellect, double agility, double will, double power)
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
	//	double hBounty = (((2 * agility) + (2 * will) + power) * 0.12);
		double hGift = ((2 * agility) + (2 * will) + power) * 0.1;
	//	double hBoost = ((2 * agility) + (2 * will) + power) * 0.08;
//		double addHealth = ((2 * agility) + (2 * will) + power) * 0.06;
		//string builder	
   		stats += "Gabe: " + (df.format(dealer)) + "\nGeber: " + (df.format(giver));
		stats += "\nBringer/Gunst: " + df.format(painbringer) + "\nSpruchfest/(Schul)-fest: " + df.format(proof);
		stats += "\nSpruchtrotz/(Schul)-frei: " + df.format(defy) + "\nBann: " + df.format(ward);
		stats += "\nOberkritiker: " + df.format(critstriker) + "\nKritiker: " + df.format(crithitter);
		stats += "\n(Schule)Recke: " + df.format(schoolassailant) + "\n(Schule) Schläger: " + df.format(schoolstriker);
		stats += "\nVerteidiger: " + df.format(defender) + "\nBlocker: " + df.format(blocker);
		stats += "\nScharfer Schuss: " + df.format(sniper) + "\n(Schul)-schuss: " + df.format(shot);
		continued +=  "scharfes Auge: " + df.format(eye) + "\nRüstungsbrecher: " + df.format(breaker);
		continued += "\nRüstungspikser: " + df.format(piercer) + "\nSchwer zu betäuben: " + df.format(stunresist);
		continued += "\nKaum zu betäuben: " + df.format(stunrecal) + "\nLebendig: " + df.format(lively);
		continued += "\nVerarzter: " + df.format(healer) + "\nMediziner: " + df.format(medic) + "\nGesund: " + df.format(healthy);
		continued += "\nLebensgeschenk: " + df.format(hGift);
		//continued += "\nHealth Boost: " + df.format(hBoost) + "\nAdd Health: " + df.format(addHealth);
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