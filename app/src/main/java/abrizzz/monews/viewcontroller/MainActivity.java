package abrizzz.monews.viewcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import abrizzz.monews.R;
import abrizzz.monews.model.NewsItems;
import abrizzz.monews.parsers.ParserCinqplus;
import abrizzz.monews.parsers.ParserDefi;
import abrizzz.monews.parsers.ParserIon;
import abrizzz.monews.parsers.ParserLexpress;
import abrizzz.monews.parsers.ParserMauricien;
import abrizzz.monews.parsers.ParserTeleplus;
import abrizzz.monews.utils.CustomTabBuilder;
import abrizzz.monews.viewcontroller.viewmodel.NewsArrayAdapter;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsArrayAdapter newsAdapter;
    private ListView listView;
    private NewsItems singleton;
    private CircularProgressBar progressBar;
    private TextView connectionInfo;
    private MenuItem openInBrowser;
    public boolean lexpressDone, defiDone, ionDone, teleplusDone, mauricienDone, cinqplusDone;
    private String browserUrl;
    private int  colorPrimaryId, colorDarkId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set Nav Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        singleton = NewsItems.getSingletonInstance();

        progressBar = (CircularProgressBar) findViewById(R.id.progressbar);
        connectionInfo = (TextView) findViewById(R.id.connection_info);

        //Set Pull to refresh colors
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.red),getResources().getColor(R.color.blue),getResources().getColor(R.color.yellow),getResources().getColor(R.color.green));

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsAdapter.notifyDataSetInvalidated();
                getNewsItems();
            }
        });

        // Get news articles async
        getNewsItems();
        //Set list adapter
        listView = (ListView) findViewById(R.id.list_view);
        newsAdapter = new NewsArrayAdapter(this);
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(findViewById(android.R.id.empty));

        browserUrl = null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        openInBrowser = menu.findItem(R.id.open_in_browser);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.open_in_browser:
                CustomTabBuilder.Builder(this,colorPrimaryId,browserUrl).launchUrl(this,Uri.parse(browserUrl.toString()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(browserUrl == null){
            openInBrowser.setVisible(false);
        }else{
            openInBrowser.setVisible(true);
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.nav_all:
                newsAdapter.src = NewsArrayAdapter.Sources.ALL;
                this.getSupportActionBar().setTitle(getString(R.string.app_name));
                colorPrimaryId = R.color.colorPrimary;
                colorDarkId = R.color.colorPrimaryDark;
                browserUrl = null;
                updateList();
                break;
            case R.id.nav_lexpress:
                newsAdapter.src = NewsArrayAdapter.Sources.LEXPRESS;
                this.getSupportActionBar().setTitle(getString(R.string.lexpress));
                colorPrimaryId = R.color.lexpressPrimary;
                colorDarkId = R.color.lexpressDark;
                browserUrl=getString(R.string.lexpress_home);
                updateList();
                break;
            case R.id.nav_defi:
                newsAdapter.src = NewsArrayAdapter.Sources.DEFI;
                this.getSupportActionBar().setTitle(getString(R.string.defi));
                colorPrimaryId = R.color.defiPrimary;
                colorDarkId = R.color.defiDark;
                browserUrl = getString(R.string.defi_home);
                updateList();
                break;
            case R.id.nav_ion:
                newsAdapter.src = NewsArrayAdapter.Sources.ION;
                this.getSupportActionBar().setTitle(getString(R.string.ion));
                colorPrimaryId = R.color.ionPrimary;
                colorDarkId = R.color.ionDark;
                browserUrl = getString(R.string.ion_home);
                updateList();
                break;
            case R.id.nav_cinqplus:
                newsAdapter.src = NewsArrayAdapter.Sources.CINQPLUS;
                colorPrimaryId = R.color.cinqplusPrimary;
                colorDarkId = R.color.cinqplusDark;
                this.getSupportActionBar().setTitle(getString(R.string.cinqplus));
                browserUrl = getString(R.string.cinqplus_home);
                updateList();
                break;
            case R.id.nav_mauricien:
                newsAdapter.src = NewsArrayAdapter.Sources.MAURICIEN;
                colorPrimaryId = R.color.mauricienPrimary;
                colorDarkId = R.color.mauricienDark;
                this.getSupportActionBar().setTitle(getString(R.string.mauricien));
                openInBrowser.setVisible(true);
                browserUrl = getString(R.string.mauricien_home);
                updateList();
                break;
            case R.id.nav_teleplus:
                newsAdapter.src = NewsArrayAdapter.Sources.TELEPLUS;
                colorPrimaryId = R.color.teleplusPrimary;
                colorDarkId = R.color.teleplusDark;
                this.getSupportActionBar().setTitle(getString(R.string.teleplus));
                browserUrl = getString(R.string.teleplus_home);
                updateList();
                break;
            case R.id.nav_about:
                displayAbout();
                break;
            case R.id.nav_reddit:
                openLinkIntent("http://www.reddit.com/r/mauritius");
                break;
            case R.id.nav_feedback:
                openLinkIntent("https://play.google.com/store/apps/details?id=abrizzz.monews");
                break;
            case R.id.nav_settings:
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setColor();
        invalidateOptionsMenu();
        return true;
    }


    public void setColor()
    {
        int colorPrimary;
        int colorDark;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            colorPrimary = getResources().getColor(colorPrimaryId,null);
            colorDark = getResources().getColor(colorDarkId,null);
        }
        else
        {
            colorPrimary = getResources().getColor(colorPrimaryId);
            colorDark = getResources().getColor(colorDarkId);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window w = getWindow();
            w.setNavigationBarColor(colorDark);
            w.setStatusBarColor(colorDark);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(colorPrimary));
    }
    public void updateList()
    {
        if(lexpressDone && defiDone && ionDone && teleplusDone && cinqplusDone && mauricienDone) {
            newsAdapter.updateAllList();
            newsAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            listView.setSelectionAfterHeaderView();
        }
    }

    public Boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        if(connected){
            connectionInfo.setVisibility(View.GONE);
        }else
        {
            connectionInfo.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        return connected;
    }


    public void getNewsItems()
    {
        if(isConnected()) {
            singleton.clearAllList();

            lexpressDone = false;
            ParserLexpress pe = new ParserLexpress(this);
            pe.execute();

            defiDone = false;
            ParserDefi pd = new ParserDefi(this);
            pd.execute();

            ionDone = false;
            ParserIon pi = new ParserIon(this);
            pi.execute();

            teleplusDone = false;
            ParserTeleplus pt = new ParserTeleplus(this);
            pt.execute();

            cinqplusDone = false;
            ParserCinqplus pc = new ParserCinqplus(this);
            pc.execute();

            mauricienDone = false;
            ParserMauricien pm = new ParserMauricien(this);
            pm.execute();
        }
    }

    // Displays a dialog info about the app
    public void displayAbout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final SpannableString msg = new SpannableString(getText(R.string.app_info));
        Linkify.addLinks(msg,Linkify.WEB_URLS);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(msg);
        AlertDialog infoDialog = builder.create();
        infoDialog.show();
    }

    //Intent to open Mauritius subreddit
    public void openLinkIntent(String url)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        newsAdapter.notifyDataSetChanged();
    }
}
