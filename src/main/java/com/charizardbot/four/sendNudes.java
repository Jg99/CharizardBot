package com.charizardbot.four;
import java.util.Random;
public class sendNudes {
	private String[] nudes = new String[32];
	public sendNudes()
	{
		// NUDE MEME DB
		nudes[0] = "https://i.imgur.com/gP25GWh.jpg";
		nudes[1] = "https://ih0.redbubble.net/image.318198938.8730/flat,800x800,075,f.u1.jpg";
		nudes[2] = "https://cdn.totalfratmove.com/wp-content/uploads/2016/12/cffe504f0455a30ed8d71e121a7d02da.jpg";
		nudes[3] = "https://i.imgur.com/YjwtKmg.jpg";
		nudes[4] = "https://pics.esmemes.com/im-sad-send-nudes-delivered-morty-is-a-babe-11624053.png";
		nudes[5] = "https://i.imgur.com/3DoIWIy.png";
		nudes[6] = "https://i.imgur.com/YkSvNvV.jpg";
		nudes[7] = "https://i.imgur.com/GB39tnS.jpg";
		nudes[8] = "https://i.imgur.com/wIljdka.jpg";
		nudes[9] = "https://i.imgur.com/7ArwEPc.jpg";
		nudes[10] = "https://i.imgur.com/UsVlwzF.jpg";
		nudes[11] = "https://i.imgur.com/Gktbnwa.jpg";
		nudes[12] = "https://i.imgur.com/do4Vt90.jpg";
		nudes[13] = "https://i.imgur.com/YVFQoZ9.jpg";
		nudes[14] = "https://pics.onsizzle.com/send-nudes-jiraiya-last-message-credits-to-the-owner-12932598.png";
		nudes[15] = "https://i.pinimg.com/originals/38/75/7e/38757e83886fabf15250bc6567bdd0e6.jpg";
		nudes[16] = "https://cdn.totalfratmove.com/wp-content/uploads/2016/12/93cd2583164da85b660f6ed7a3a962e8.jpg";
		nudes[17] = "https://i.imgur.com/DwV1dj2.jpg";
		nudes[18] = "http://www.ildolomiti.it/sites/default/files/styles/articolo/public/articoli/15895264_1859624247593187_1841694907979923657_n.jpg?itok=K85fVrom";
		nudes[19] = "https://i.redd.it/g385eqiysjny.jpg";
		nudes[20] = "https://img-9gag-fun.9cache.com/photo/aDzZ1LO_700b.jpg";
		nudes[21] = "https://i.imgur.com/xq1Hvle.jpg";
		nudes[22] = "https://i.redd.it/x56f2q7f5km21.jpg";
		nudes[23] = "https://i.redd.it/mmzc7rkdn8r21.jpg";
		nudes[24] = "https://i.imgur.com/Xvvpr9O.jpg";
		nudes[25] = "https://i.imgur.com/YWZMoFO.jpg";
		nudes[26] = "https://i.imgur.com/rlif22K.jpg";
		nudes[27] = "https://i.imgur.com/rX8U0hG.jpg";
		nudes[28] = "https://pics.me.me/today-3-10-pm-send-nudes-ere-is-donkey-kong-without-35244107.png";
		nudes[29] = "https://i.pinimg.com/originals/36/87/60/36876066a2c90907f3d5b40577985210.jpg";	
		nudes[30] = "https://i.imgur.com/2x1YuH4.jpg";
		nudes[31] = "https://i.redd.it/ga9b7o2e21g41.jpg";
	}
	public String getNude()
	{
		Random rand = new Random();
		Random randSeed = new Random();
		rand.setSeed(randSeed.nextInt(1000000));
		int randNude = rand.nextInt(nudes.length);
		return nudes[randNude];
	}
}