package com.cs.shop.main_classes;

import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;

import java.util.ArrayList;

public class ManageUser extends DataSetup {

    public static final int NOT_ERROR = 0;
    public static final int ERROR_NOT_ENABLE = -1;
    public static final int ERROR_USERNAME_OR_PASSWORD = -3;

    public static final int ACCESS_ADMIN = 4;
    public static final int ACCESS_SALE = 5;
    public static final int ACCESS_SEARCH = 6;

    private ArrayList<UserDetails> users;

    public ManageUser(Context context) {
        super(context);
        users = super.users();
    }

    public ArrayList<UserDetails> getUsers() {
        return this.users;
    }

    public ArrayList<UserDetails> getUsers(Filter.User filter) {
        filter.sort(this.users);
        return this.users;
    }

    public UserDetails searchUser(int userId) {
        for (UserDetails user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public UserDetails searchUser(String username) {
        for (UserDetails user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean newUser(UserDetails user) {
        boolean result = super.insert(user);
        refresh();
        return result;
    }

    public boolean updateUser(UserDetails user) {
        boolean result = super.update(user);
        refresh();
        return result;
    }

    public void deleteUser(UserDetails user) {
        super.delete(user);
        refresh();
    }

    public int login(String username, String password){
        UserDetails user = searchUser(username);
        if (user == null || !user.getPassword().equals(password)) return ERROR_USERNAME_OR_PASSWORD;
        if (!user.getEnabled()) return ERROR_NOT_ENABLE;
        return NOT_ERROR;
    }

    public void refresh(){
        this.users =super.users();
    }


}