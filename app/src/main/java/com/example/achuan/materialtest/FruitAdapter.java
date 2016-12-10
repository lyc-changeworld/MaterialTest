package com.example.achuan.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by achuan on 16-12-9.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;//显示上下文
    private List<Fruit> mFruitList;//列表绑定的数据集合引用变量

    public FruitAdapter(List<Fruit> fruitList) {
        this.mFruitList = fruitList;
    }

    //布局装载类,通过该类,使布局控件只需装载一次就行,保存到viewHolder实例中去了
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_fruit)
        ImageView mIvFruit;
        @BindView(R.id.tv_name)
        TextView mTvName;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //item第一次显示时,才创建其对应的viewholder实例并进行控件存储,
    //之后在显示时直接调用onBindViewHolder方法获取对应的viewholder实例使用即可
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();//获取上下文对象
        }
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        /***为每个item设置点击监听事件***/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();//获取item对应的位置
                Fruit fruit=mFruitList.get(position);//拿到对应的数据实体
                //创建一个跳转意图,打开内容显示activity
                Intent intent=new Intent(mContext,FruitActivity.class);
                //传递水果的名称和图片id号到水果内容展示的activity
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);//开始跳转
            }
        });
        return holder;
    }
    //对每个item的数据进行赋值,在每个子项被滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取item对应的实体类对象
        Fruit fruit=mFruitList.get(position);
        holder.mTvName.setText(fruit.getName());
        //通过Glide来加载图片
        Glide.with(mContext).//传入上下文(Context|Activity|Fragment)
                load(fruit.getImageId()).//加载图片,传入(URL地址｜资源id｜本地路径)
                into(holder.mIvFruit);//将图片设置到具体某一个IV中

    }
    //告诉rv有多少个子项
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }



}
