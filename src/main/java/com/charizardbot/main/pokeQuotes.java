package com.charizardbot.main;
import java.util.Random;
public class pokeQuotes {
	private String[] quotes = new String[36];
	public pokeQuotes()
	{
		// Pokémon quote DB
		quotes[0] = "\"Even If we don’t understand each other, that’s not a reason to reject each other. There are two sides to any argument. Is there one point of view that has all the answers? Give it some thought.\" – Alder";
		quotes[1] = "\"Everybody makes a wrong turn once in a while\"– Ash Ketchum";
		quotes[2] = "\"A Caterpie may change into a Butterfree, but the heart that beats inside remains the same.\" – Brock";
		quotes[3] = "\"Make your wonderful dream a reality, it will become your truth. If anyone can, it’s you.\" – N, Pokemon Black/White";
		quotes[4] = "\"I see now that one’s birth is irrelevant. It’s what you do that determines who you are.\" – Mewtwo";
		quotes[5] = "\"Take charge of your destiny.\" - Rayquaza";
		quotes[6] = "\"There’s no sense in going out of your way to get somebody to like you.\" – Ash Ketchum";
		quotes[7] = "\"You see, sometimes friends have to go away, but a part of them stays behind with you.\" – Ash Ketchum";
		quotes[8] = "\"We do have a lot in common. The same earth, the same air, the same sky. Maybe if we started looking at what’s the same, instead of looking at what’s different, well, who knows?\" – Meowth";
		quotes[9] = "\"The important thing is not how long you live. It’s what you accomplish with your life.\" – Grovyle";
		quotes[10] = "\"I will show you that my love for my friends permeates every cell in my body.\" – N, Pokemon Black/White";
		quotes[11] = "\"Me give up? No way!\" – Ash Ketchum";
		quotes[12] = "\"Pika pika!\" - Pikachu";
		quotes[13] = "\"Char char!\" - Charmander";
		quotes[14] = "\"We're blasting off again!\" - Team Rocket";
		quotes[15] = "Prepare for trouble!\n" + 
				"And make it double!\n" + 
				"To protect the world from devastation!\n" + 
				"To unite all peoples within our nation!\n" + 
				"To denounce the evils of truth and love!\n" + 
				"To extend our reach to the stars above!\n" + 
				"Jessie!\n" + 
				"James!\n" + 
				"Team Rocket blasts off at the speed of light!\n" + 
				"Surrender now, or prepare to fight!\n" + 
				"\n" + 
				"Meowth!\n" + 
				"That's right!";
		quotes[16] = "\"If I'm wearing a bikini...\nwhere do I put my Pokéballs?\nTeehee... woman's secret!\" - Swimmer Kylie";
		quotes[17] = "\"It's tough fetching Poké Balls from my bikini!\" - Swimmer Joyce";
		quotes[18] = "\"A wildfire destroys everything in its path. It will be the same with your powers unless you learn to control them.\" - Giovanni";
		quotes[19] = "\"That’s ok, Brock – you’ll find lots of other girls to reject you!\" - Ash Ketchum";
		quotes[20] = "\"Some trainers have no fear. To them this is just one more challenge. They follow their hearts. That is what sets them apart, and will make them Pokémon Masters. Good luck to all of you.\" - Miranda";
		quotes[21] = "\"Just when you give him a break, he has to be a nice guy, too.\" - Misty";
		quotes[22] = "\"Behold my powers! I am the strongest Pokémon in the world. Stronger even than Mew.\" - MewTwo";
		quotes[23] = "\"Do you always need a reason to help somebody?\" - Ash Ketchum";
		quotes[24] = "\"Hey, how can we breathe underwater?\" - James (Team Rocket)";
		quotes[25] = "\"Your Charizard is poorly trained.\" - MewTwo";
		quotes[26] = "\"Rejected…by the only girl I ever loved!\" - Brock";
		quotes[27] = "\"This Nurse Joy’s Bangs are 1 cm longer than any other Joy in the country..\" - Brock";
		quotes[28] = "\"Heheh! This GYM is great! It's full of women!\" - NPC in front of Celedon City Gym";
		quotes[29] = "\"By the way...boy...you ever had a lover?\" - Hiker Andy";
		quotes[30] = "\"My Rattata is different from regular Rattata. It’s like my Rattata is in the top percentage of Rattatas.\" - Youngster Joey";
		quotes[31] = "\"My body is ready.\" - Veteran Timeo (totally not Reggie Fils-Amie";
		quotes[32] = "\"Lightyears isn't time... It measures distance.\" - Jr Trainer, Pewter City Gym";
		quotes[33] = "\"Give...me...blood…\" - Lavendertown (R/B/Y) NPC";
		quotes[34] = "\"If you need to make a difficult decision and you let someone else decide for you, you will regret it, no matter how it turns out.\" - Route 104 NPC (R/S/E)";
		quotes[35] = "\"It's a $10 million fine if you're late!\" - Barry/Pearl (D/P/PT)";
	}
	public String getQuote()
	{
		Random rand = new Random();
		int randQuote = rand.nextInt(quotes.length);
		return quotes[randQuote];
	}
}