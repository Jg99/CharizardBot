package com.charizardbot.four;
import java.util.Random;
import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.github.kskelm.baringo.model.TagGallery;
import com.github.kskelm.baringo.model.gallery.*;
import com.github.kskelm.baringo.model.gallery.GalleryItem.Sort;
import com.github.kskelm.baringo.model.gallery.GalleryItem.Window;
/**
 * Imgur API login and search functions
 */
public class ImgurAPI {
	private BaringoClient mClient;
	private GalleryItem image;
	public int rateLimitCount = 500;
	public boolean isError = false;
	public void authenticate()
	{
		 	String clientId = Main.IMGUR_ID; // from registration
		    String clientSecret = Main.IMGUR_SECRET; // from registration
		    try {
		        BaringoClient client = new BaringoClient.Builder()
		                .clientAuth( clientId, clientSecret )
		                .build();
		        mClient = client;
		    } catch (BaringoApiException e) {
		        e.printStackTrace();
		    }       
	}
	public void searchImage (String searchTerm) {
		try {
		if (rateLimitCount > 10) {
		this.isError = false;
		Sort sort = Sort.Top;
		Window window = Window.All;
		TagGallery gallery = mClient.galleryService().getTagGallery(searchTerm, sort, window, 0);
		GalleryItem firstImage = gallery.getItems().get(0);
		this.image = firstImage;
		rateLimitCount = mClient.getQuota().getUserCreditsAvailable();
		}
		} catch (Exception e) { this.isError = true;}
	}
	public void searchRandImage (String searchTerm) { //first 10 pages/first 5 images
		try {
		if (rateLimitCount > 10) {
		this.isError = false;
		Sort sort = Sort.Top;
		Window window = Window.All;
		Random rand = new Random();
		TagGallery gallery = mClient.galleryService().getTagGallery(searchTerm, sort, window, 0);
		int randImage = rand.nextInt(gallery.getItems().size()); // randomizes the number of images on the first page.
		GalleryItem randImg = gallery.getItems().get(randImage);
		
		this.image = randImg;
		rateLimitCount = mClient.getQuota().getUserCreditsAvailable();
		}
		} catch (Exception e) { this.isError = true;}	
	}
	public String returnImageLink() {
		return image.getLink();
	}
	public int returnRateLimit() {
		return rateLimitCount;
	}
	public String returnImageInfo() {
		String info = "";
		try {
		 info = "Link: " + image.getLink() + "\nVotes: " + image.getScore();
		} catch (Exception e) {this.isError = true;}
		return info;
	}
	public boolean returnError() {
		return this.isError;
	}
}