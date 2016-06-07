package abrizzz.monews.viewcontroller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.ListView;

import abrizzz.monews.R;
import abrizzz.monews.model.NewsItems;
import abrizzz.monews.parsers.ParserDefi;
import abrizzz.monews.parsers.ParserIon;
import abrizzz.monews.parsers.ParserLexpress;
import abrizzz.monews.parsers.ParserTeleplus;
import abrizzz.monews.viewcontroller.viewmodel.NewsArrayAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsArrayAdapter newsAdapter;
    private ListView listView;
    private NewsItems singleton;

    public boolean lexpressDone, defiDone, ionDone, teleplusDone, mauricienDone, cinqplusDone;

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

        //Set Pull to refresh colors
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F44336"), Color.parseColor("#2196F3"), Color.parseColor("#FFC107"),Color.parseColor("#4CAF50"));
        swipeRefreshLayout.measure(1,1); //workaround to make animation show at when loading first time
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                singleton.clearDefiItems();
                singleton.clearLexpressItems();
                singleton.clearIonItems();
                singleton.clearTeleplusItems();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
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
                setColor(R.color.colorPrimary,R.color.colorPrimaryDark);
                updateList();
                break;
            case R.id.nav_lexpress:
                newsAdapter.src = NewsArrayAdapter.Sources.LEXPRESS;
                this.getSupportActionBar().setTitle(getString(R.string.lexpress));
                setColor(R.color.lexpressPrimary,R.color.lexpressDark);
                updateList();
                break;
            case R.id.nav_defi:
                newsAdapter.src = NewsArrayAdapter.Sources.DEFI;
                this.getSupportActionBar().setTitle(getString(R.string.defi));
                setColor(R.color.defiPrimary,R.color.defiDark);
                updateList();
                break;
            case R.id.nav_ion:
                newsAdapter.src = NewsArrayAdapter.Sources.ION;
                this.getSupportActionBar().setTitle(getString(R.string.ion));
                setColor(R.color.ionPrimary,R.color.ionDark);
                updateList();
                break;
            case R.id.nav_cinqplus:
                newsAdapter.src = NewsArrayAdapter.Sources.CINQPLUS;
                setColor(R.color.cinqplusPrimary,R.color.cinqplusDark);
                this.getSupportActionBar().setTitle(getString(R.string.cinqplus));
                updateList();
                break;
            case R.id.nav_mauricien:
                newsAdapter.src = NewsArrayAdapter.Sources.MAURICIEN;
                setColor(R.color.mauricienPrimary,R.color.mauricienDark);
                this.getSupportActionBar().setTitle(getString(R.string.mauricien));
                updateList();
                break;
            case R.id.nav_teleplus:
                newsAdapter.src = NewsArrayAdapter.Sources.TELEPLUS;
                setColor(R.color.teleplusPrimary,R.color.teleplusDark);
                this.getSupportActionBar().setTitle(getString(R.string.teleplus));
                updateList();
                break;
            case R.id.nav_about:
                displayAbout();
                break;
            case R.id.nav_reddit:
                openReddit();
                break;
            case R.id.nav_settings:
                Intent settingsActivity = new Intent(this,SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setColor(int colorPrimaryId, int colorDarkId)
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
        if(lexpressDone && defiDone && ionDone && teleplusDone) {
            newsAdapter.updateAllList();
            newsAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void getNewsItems()
    {
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
    public void openReddit()
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://m.reddit.com/r/Mauritius"));
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
