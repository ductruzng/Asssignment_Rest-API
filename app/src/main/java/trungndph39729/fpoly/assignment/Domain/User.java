package trungndph39729.fpoly.assignment.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String _id, username, email,password, name,avatar,phoneNo;
    private ArrayList cart,orders,addresses;

    public User() {
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getCart() {
        return cart;
    }

    public void setCart(ArrayList cart) {
        this.cart = cart;
    }

    public ArrayList getOrders() {
        return orders;
    }

    public void setOrders(ArrayList orders) {
        this.orders = orders;
    }

    public ArrayList getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList addresses) {
        this.addresses = addresses;
    }

    public User(String _id, String username, String email, String password, String name, ArrayList cart, ArrayList orders, ArrayList addresses) {
        this._id = _id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cart = cart;
        this.orders = orders;
        this.addresses = addresses;
    }
}
