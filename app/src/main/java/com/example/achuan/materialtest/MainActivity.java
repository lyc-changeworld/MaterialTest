package com.example.achuan.materialtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private FruitAdapter mFruitAdapter;
    private List<Fruit> fruitList = new ArrayList<>();

    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);//将toolbar实例传入
        ActionBar actionBar = getSupportActionBar();//获取传入的toolbar实例
        //为toolbar添加导航按钮
        if (actionBar != null) {
            //让导航按钮显示出来,true:显示,false:不显示(默认效果)
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置自定义的导航按钮图标,如不设置,则使用系统默认生成的图标(指向左边的箭头)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        /***1-左侧滑动滑动菜单栏的监听事件***/
        //设置默认选中的item项
        mNavView.setCheckedItem(R.id.backup);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.backup:
                        //针对不同的item实现不同的逻辑处理
                        break;
                    default:
                        break;
                }
                //点击左侧菜单栏中的item后将滑动菜单关闭
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        /***2-设置数据集合,并将数据通过适配器显示到列表中去***/
        initFruits();
        mFruitAdapter = new FruitAdapter(fruitList);
        //常用的布局：Linear(线性)｜Grid(大小一样的网格)|StaggeredGrid(大小不一样的网格)
        //1- 线性布局(单列或单行)(参数介绍：1-Context)
        //LinearLayoutManager linearlayoutManager=new LinearLayoutManager(this);
        //linearlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //2- 规则式网格布局(参数介绍：1-Context,2-列数)
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 3);
        //gridlayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);//设置方向
        //3- 瀑布流式(不规则)网格布局(参数介绍：1-列数,2-方向)
        //StaggeredGridLayoutManager staggeredGridLayoutManager=
        //new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridlayoutManager);//为列表添加布局
        mRecyclerView.setAdapter(mFruitAdapter);//为列表添加适配器

        /***3-初始化下拉刷新控件***/
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);//设置进度条的颜色
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }

    //刷新数据集合的方法
    private void refreshFruits(){
        //开启一个子线程来进行刷新工作
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将线程切换回主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();//重新初始化数据集合
                        mFruitAdapter.notifyDataSetChanged();//刷新列表显示
                        mSwipeRefresh.setRefreshing(false);//隐藏刷新进度条
                    }
                });
            }
        }).start();
    }

    //初始化数据集的方法
    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();//获取一个随机数实例
            //获去集合[0,fruits.length-1]中的随机一个数
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //重写创建menu菜单的方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().//获得MenuInflater对象
                inflate(R.menu.toolbar,//指定通过哪一个资源文件来创建菜单
                menu);
        return true;//返回true,表示允许创建的菜单显示出来
    }

    //重写菜单item的点击事件的方法
    //HomeAsUp按钮的id永远都是android.R.id.home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //点击按钮打开显示左边的菜单,这里设置其显示的行为和XML中定义的一致
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "clicked backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "clicked settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;//返回true,表示允许item点击响应
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //Toast.makeText(this, "fab clicked", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                        //前面三个参数和Toast类似
                        //下面调用一个方法来设置一个动作按钮,使可以和用户进行交互
                        .setAction("Undo", new View.OnClickListener() {
                            //点击动作按钮后,Snackbar会立马消失
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Data restored",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();//将Snackbar显示出来
                break;
            default:
                break;
        }
    }
}
