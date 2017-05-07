package com.example.jay3165.garglibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class AllCourseActivity extends Activity implements AllCourseAdapter.Clickable,NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rvCourse;
    public NavigationView  navigationView;
    CustomeAdpter adpter;
    ViewPager viewPager;
    RelativeLayout rlaction;
    ImageView img;

    Toolbar toolbar;
    TextView tv;
    Button cart;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler = new DBHandler(this);
    private RecyclerView.LayoutManager courseActivityLayout;
    ArrayList<course> CourseArrayList = new ArrayList<>();
    ArrayList<BookAddToCart> cartArrayList=new ArrayList<>();
    AllCourseAdapter.Clickable clickable = this;
    SearchView searchView;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent i=new Intent(AllCourseActivity.this,login_activity.class);
            startActivity(i);

        } else if (id == R.id.nav_regi) {
            Intent i=new Intent(AllCourseActivity.this,Registration_Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_contcatus) {
            Intent i=new Intent(AllCourseActivity.this,ContactUs1.class);
            startActivity(i);

        } else if (id == R.id.nav_aboutus) {
            Intent i=new Intent(AllCourseActivity.this,Aboutus.class);
            startActivity(i);

        } else if (id == R.id.nav_myorder) {
            Intent i=new Intent(AllCourseActivity.this,Myorder.class);
            startActivity(i);

        }
        else if (id==R.id.nav_changepass){
            Intent i=new Intent(AllCourseActivity.this,ChangePassword.class);
            startActivity(i);
        }
        else if(id== R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.google.garglibrary/");
            startActivity(Intent.createChooser(intent, "Share with"));

        }
        else if(id==R.id.nav_logout){
            editor.putBoolean(Loginpref.ISUSERLIOGIN,false).apply();
            Intent i=new Intent(AllCourseActivity.this,login_activity.class);

            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course);
        cartArrayList=dbHandler.getAllBookDetails();
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        rvCourse = (RecyclerView)findViewById(R.id.rvCourse);
         tv=(TextView)findViewById(R.id.tvaction);
        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean(Loginpref.ISUSERLIOGIN, false)){
            Menu nav_Menu=navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_changepass).setVisible(true);
        }
        else {
            Menu nav_Menu=navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_changepass).setVisible(false);
        }

        img=(ImageView)findViewById(R.id.imgnotify);
        if(cartArrayList.size()==0){
            img.setVisibility(View.GONE);
        }else {
            img.setVisibility(View.VISIBLE);

        }
        cart=(Button)findViewById(R.id.btn_cart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("sizeeeee",cartArrayList.size()+"");

                if (cartArrayList.size()==0){
                    Toast.makeText(getApplicationContext(),"CART IS EMPTY",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i=new Intent(AllCourseActivity.this,AllCartActivity.class);
                    startActivity(i);
                }
            }


        });
        searchView = (SearchView)findViewById(R.id.searchview);
       searchView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (searchView.isActivated()){
                   tv.setVisibility(View.GONE);
               }
           }
       });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent i =new Intent(AllCourseActivity.this,search_book.class);
                i.putExtra("s",query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new mytimertask(),2000,4000);
        adpter=new CustomeAdpter(this);
        viewPager.setAdapter(adpter);
        courseActivityLayout = new GridLayoutManager(this,2);
        rvCourse.setLayoutManager(courseActivityLayout);
        rvCourse.setHasFixedSize(true);

    }

public class mytimertask extends TimerTask{

    @Override
    public void run() {
        AllCourseActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(viewPager.getCurrentItem()==0){
                    viewPager.setCurrentItem(1);
                }else if(viewPager.getCurrentItem()==1){
                    viewPager.setCurrentItem(2);
                }else {
                    viewPager.setCurrentItem(0);
                }
            }
        });
    }
}


    @Override
    protected void onStart() {
        super.onStart();
        if (CourseArrayList.size() == 0) {
            CourseData();
        }
    }
    //fetch Course data
    public void CourseData() {

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-Type", "application/json");
        mClient.get(this, "http://khedutstore.com/garglibrary/ws/main-cat.php", new JsonHttpResponseHandler() {

                    ProgressDialog mProgressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray mJsonArray = response.getJSONArray("details");
                            JSONObject mJsonObject;

                            for (int i = 0; i < mJsonArray.length(); i++) {
                                mJsonObject = mJsonArray.getJSONObject(i);
                                CourseArrayList.add(new course(mJsonObject.getString("main_cat_id"), mJsonObject.getString("main_cat_name")));
                                Log.e("Array Length", CourseArrayList.size() + "");

                                rvCourse.setAdapter(new AllCourseAdapter(CourseArrayList, getApplicationContext(),clickable));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mProgressDialog = ProgressDialog.show(AllCourseActivity.this, "Loading", "Please Wait", true, false);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }
                }

        );

    }

    @Override
    public void click(int position) {

            Intent i = new Intent(AllCourseActivity.this, AllSemActivity.class);
            i.putExtra("maincategory_id", CourseArrayList.get(position).getMain_cat_id());
            startActivity(i);

            Toast.makeText(this, CourseArrayList.get(position).getMain_cat_name(), Toast.LENGTH_SHORT).show();


    }
}

