package com.rahul.hackathon.spm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.hackathon.spm.database.PassEntryInfoStorage;
import com.rahul.hackathon.spm.model.Entry;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PassEntryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUp();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        TextView emptyView = (TextView) findViewById(R.id.emptyview);

        PassEntryInfoStorage passEntryInfoStorage = new PassEntryInfoStorage(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        List<Entry> entries = passEntryInfoStorage.getAllPassEntries();

        if (entries.size() == 0) {
            // show empty view
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {
            // show the list
            mAdapter = new PassEntryAdapter(entries);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void openPopUp(){
        View inputView = LayoutInflater.from(this).inflate(R.layout.layout_input_pass_entry, null);
        final EditText etTitle = (EditText) inputView.findViewById(R.id.title);
        final EditText etUsername = (EditText) inputView.findViewById(R.id.username);
        final EditText etPassword = (EditText) inputView.findViewById(R.id.password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(inputView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString();
                        String username = etUsername.getText().toString();
                        String password = etPassword.getText().toString();
                        Entry entry = new Entry(title, username, password);
                        PassEntryInfoStorage passEntryInfoStorage =
                                new PassEntryInfoStorage(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                        passEntryInfoStorage.addPassEntry(entry);
                        Snackbar.make(findViewById(R.id.pagecontainer), "Added Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    }
                });
        builder.create().show();
    }
}
