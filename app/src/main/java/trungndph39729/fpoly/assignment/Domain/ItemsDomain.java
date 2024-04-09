package trungndph39729.fpoly.assignment.Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsDomain implements Serializable {
    private String title;
    private String description;
    private double price;
    private double rating;
    private int review;
    private ArrayList<String> picUrl;

    @SerializedName("id_category")
    private CategoryDomain categoryDomain;

    private int numberInCart;

    public ItemsDomain() {
    }

    public ItemsDomain(String title, String description,CategoryDomain categoryDomain, ArrayList<String> picUrl, double price, double rating, int review) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.categoryDomain =categoryDomain;
        this.rating = rating;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public CategoryDomain getCategoryDomain() {
        return categoryDomain;
    }

    public void setCategoryDomain(CategoryDomain categoryDomain) {
        this.categoryDomain = categoryDomain;
    }
}
