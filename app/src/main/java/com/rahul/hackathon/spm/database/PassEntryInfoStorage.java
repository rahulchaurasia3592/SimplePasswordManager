package com.rahul.hackathon.spm.database;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rahul.hackathon.spm.model.Entry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A file for storing the password entries to the shared preferences.
 *
 * Created by rahul on 19/12/15.
 */
public class PassEntryInfoStorage {

    private SharedPreferences mPreferences;
    private Gson gson;
    private String PREF_LIST = "pref_entries_list";

    public PassEntryInfoStorage(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
        gson = new Gson();
    }


    /**
     * Adds the password entry to the shared preferences
     *
     */
    public boolean addPassEntry(Entry entry) {
        boolean isAdded = false;
        List<Entry> items = getAllPassEntries();
        items.add(0, entry);
        String jsonData = gson.toJson(items);
        mPreferences.edit().putString(PREF_LIST, jsonData).commit();
        return isAdded;
    }

    /**
     * Returns all the entries that are saved on the shared preferences by
     * the user.
     *
     */
    public List<Entry> getAllPassEntries() {
        List<Entry> entries = new ArrayList<>();
        String entriesInString = mPreferences.getString(PREF_LIST, null);
        if (entriesInString != null) {
            Type type = new TypeToken<List<Entry>>(){}.getType();
            entries = new Gson().fromJson(entriesInString, type);
        }

        return entries;
    }
}
