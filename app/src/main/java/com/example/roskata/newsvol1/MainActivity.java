package com.example.roskata.newsvol1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String URL_REQUEST = "https://content.guardianapis.com/search?show-tags=contributor&show-fields=thumbnail&page=20&api-key=c56b5ed0-1a11-4d00-bac6-7bbca00c3081";
    private static final int ARTICLE_LOADER_ID = 1;
    private ArticleAdapter articleAdapter;
    private ProgressBar progressBar;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.loading_indicator);
        emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        listView.setAdapter(articleAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_connection);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(articleAdapter.getItem(position).getUrl()));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, URL_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        emptyView.setText(R.string.no_data);
        progressBar.setVisibility(View.GONE);
        articleAdapter.clear();

        if (data != null && !data.isEmpty()) {
            articleAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) { articleAdapter.clear(); }
}