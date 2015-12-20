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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.perk.perksdk.PerkListener;
import com.perk.perksdk.PerkManager;
import com.rahul.hackathon.spm.database.PassEntryInfoStorage;
import com.rahul.hackathon.spm.model.Entry;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PassEntryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView emptyView;
    private int REWARD = 2;

    //This is our application's API key the Appsaholic Dashboard.
    String key = "00cd238bf2b2bbcdcfa2e6b4e3d6e909481bea9e";

    //This is our earn event from the Appsaholic Dashboard.
    String earnEvent = "f071a6d4d0464fec196b2c030374db3957ea74c1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle the perk
        final PerkListener localPerkListener = new PerkListener() {
            @Override
            public void onPerkEvent(String reason) {
                //Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
            }
        };
        PerkManager.startSession(this, key, localPerkListener);

        // Handle the fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUp();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        emptyView = (TextView) findViewById(R.id.emptyview);
        PassEntryInfoStorage passEntryInfoStorage = new PassEntryInfoStorage(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        List<Entry> entries = passEntryInfoStorage.getAllPassEntries();
        mAdapter = new PassEntryAdapter(entries);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        doNeedToShowEmptyView();
    }

    private void doNeedToShowEmptyView() {
        if (mAdapter.getItemCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else if (mAdapter.getItemCount() > 0){
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_portal) {
            PerkManager.showPortal(this, key);
            return true;
        }  if (id == R.id.action_logout) {
            PerkManager.showPortal(this, key);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPopUp(){
        View inputView = LayoutInflater.from(this).inflate(R.layout.layout_input_pass_entry, null);
        final EditText etTitle = (EditText) inputView.findViewById(R.id.title);
        final EditText etUsername = (EditText) inputView.findViewById(R.id.username);
        final EditText etPassword = (EditText) inputView.findViewById(R.id.password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(inputView)
                .setTitle("Add a new entry")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString();
                        String username = etUsername.getText().toString();
                        String password = etPassword.getText().toString();
                        if (title.isEmpty() || username.isEmpty() || password.isEmpty()) {
                            Toast.makeText(DashboardActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Entry entry = new Entry(title, username, password);
                        PassEntryInfoStorage passEntryInfoStorage =
                                new PassEntryInfoStorage(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                        passEntryInfoStorage.addPassEntry(entry);
                        Snackbar.make(findViewById(R.id.pagecontainer), "Added Successfully", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mAdapter.addEntry(entry);
                        doNeedToShowEmptyView();

                        // Handle the reward count
                        int itemCount = mAdapter.getItemCount();
                        if (!(itemCount % REWARD < 0) && !(itemCount % REWARD > 0)) {
                            PerkManager.trackEvent(DashboardActivity.this, key, earnEvent, true, null);
                        }
                    }
                });
        builder.create().show();
    }


}
