package com.charizardbot.main;
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
		if (strength > 500 || strength < 0 || intellect > 500 || intellect < 0 || agility > 500 || agility < 0 || will > 500 || will < 0 || power > 500 || power < 0)
		{
			return("Λάθος στατιστικά κατοικιδίου.");
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
   	stats += "Χάρισμα Σχολής: " + (df.format(dealer)) + "\nΠονοδότης / Σχολής: " + (df.format(giver));
		stats += "\nΠονοφόρος / Σχολής: " + df.format(painbringer) + "\nΑπρόσβλητος από Ξόρκι / Σχολή: " + df.format(proof);
		stats += "\nΑψήφηση Ξορκιού / Σχολής: " + df.format(defy) + "\nΠροστασία Σχολής: " + df.format(ward);
		stats += "\nΑρχικριτικός: " + df.format(critstriker) + "\nΚριτικός: " + df.format(crithitter);
		stats += "\nΠολεμιστής Σχολής: " + df.format(schoolassailant) + "\nΠυγμάχος Σχολής: " + df.format(schoolstriker);
		stats += "\nΑμυνόμενος: " + df.format(defender) + "\nΜπλοκαριστής: " + df.format(blocker);
		stats += "\nΕλεύθερος Σκοπευτής / Σχολής: " + df.format(sniper) + "\nΒολή Σχολής: " + df.format(shot);
		continued +=  "Μάτι Σχολής: " + df.format(eye) + "\nΠανοπλιοσπάστης: " + df.format(breaker);
		continued += "\nΤρυπητής Πανοπλιών: " + df.format(piercer) + "\nΝαρκώνεται Δύσκολα: " + df.format(stunresist);
		continued += "\nΔεν Mπορεί να Nαρκωθεί: " + df.format(stunrecal) + "\nΖωντανό: " + df.format(lively);
		continued += "\nΘεραπευτής: " + df.format(healer) + "\nΓιατρός: " + df.format(medic) + "\nΥγιές: " + df.format(healthy);
		continued += "\nΓενναιοδωρία Υγείας: " + df.format(hBounty) + "\nΟπάλιο Δώρου υγείας: " + df.format(hGift);
		continued += "\nΟπάλιο Ενίσχυσης Υγείας: " + df.format(hBoost) + "\nΠροσθήκη Υγείας: " + df.format(addHealth);
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
