package com.rahul.hackathon.spm.database;

import android.content.SharedPreferences;

import com.rahul.hackathon.spm.model.User;

/**
 * A helper file for storing and validating the users.
 *
 * Created by rahul on 19/12/15.
 */
public class UserInfoStorage {
    private SharedPreferences mPreferences;
    private String PREF_USERNAME = "pref_username";
    private String PREF_PASSWORD = "pref_password";

    public UserInfoStorage(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }

    /**
     * Saves the user to the shared preferences.
     *
     */
    public void saveUser(User user) {
        mPreferences.edit().putString(PREF_USERNAME, user.getUsername()).commit();
        mPreferences.edit().putString(PREF_PASSWORD, user.getPassword()).commit();
    }

    /**
     * Validates the username and password entered by the user.
     *
     */
    public boolean validateUser(User user) {
        boolean isValid = false;
        String username = mPreferences.getString(PREF_USERNAME, null);
        String password = mPreferences.getString(PREF_PASSWORD, null);
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Checks whether this user has registered earlier or not and
     * based on this login or registration page will be shown to him.
     *
     */
    public boolean isUserRegistered() {
        boolean isRegistered = false;

        if (mPreferences.getString(PREF_USERNAME, null) != null &&
                mPreferences.getString(PREF_PASSWORD,null) != null) {
            isRegistered = true;
        }

        return isRegistered;
    }

}
