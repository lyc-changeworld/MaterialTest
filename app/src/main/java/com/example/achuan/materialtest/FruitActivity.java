package com.example.achuan.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by achuan on 16-12-9.
 */

public class FruitActivity extends AppCompatActivity {

    @BindView(R.id.fruit_image_view)
    ImageView mFruitImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tv_fruit_content)
    TextView mTvFruitContent;

    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_IMAGE_ID="fruit_image_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        ButterKnife.bind(this);
        /*获取上一个活动传递过来的数据*/
        Intent intent=getIntent();
        String fruitName=intent.getStringExtra(FRUIT_NAME);//水果名称
        int fruitImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0);//图片id资源号
        //1-设置标题栏
        setSupportActionBar(mToolbar);//将toolbar实例传入
        ActionBar actionBar = getSupportActionBar();//获取传入的toolbar实例
        //为toolbar添加导航按钮
        if (actionBar != null) {
            //让导航按钮显示出来,true:显示,false:不显示(默认效果)
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //为折叠式标题栏添加标题文本(水果的名称)
        mCollapsingToolbar.setTitle(fruitName);
        //通过Glide加载水果的图片作为标题的背景图片
        Glide.with(this).load(fruitImageId).into(mFruitImageView);
        //获取文本信息,并显示在内容区域
        String fruitContent=createFruitContent(fruitName);
        mTvFruitContent.setText(fruitContent);
    }

    //添加返回导航按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();//点击返回导航按钮,结束当前activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //为内容显示区域添加文本数据
    private String createFruitContent(String fruitName){
        StringBuilder fruitContent=new StringBuilder();
        for (int i = 0; i <500 ; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }


}
