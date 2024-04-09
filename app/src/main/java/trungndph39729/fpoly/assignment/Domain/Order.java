package trungndph39729.fpoly.assignment.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private User user;
    private ArrayList<ItemsDomain> products;
    private Double totalPrice;

    private String createdAt;

    public Order() {
    }

    public Order(User user, ArrayList<ItemsDomain> products, Double totalPrice, String createdAt) {
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public ArrayList<ItemsDomain> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ItemsDomain> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
