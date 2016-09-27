package org.brenomachado.meutempo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private DetailActivity local = this;
    private String mForescastStr;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String forecast = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mForescastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) findViewById(R.id.textView)).setText(mForescastStr);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail, menu);

        MenuItem item = menu.findItem(R.id.action_detail_sharing);

        shareActionProvider = new ShareActionProvider(this);
        MenuItemCompat.setActionProvider(item, shareActionProvider);

        return super.onCreateOptionsMenu(menu);
    }

    private void createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                (mForescastStr + " #SunshineApp"));

        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_detail_settings:
                Intent intent = new Intent(local, SettingsActivity.class);
                startActivity(intent);

                return true;
            case R.id.action_detail_sharing:
                createShareIntent();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
