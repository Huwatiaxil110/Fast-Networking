package com.squareup.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.hello.SectorEntity;
import com.squareup.hello.TextLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.R;

import java.util.ArrayList;

/**
 * Created by zc on 2017/12/29.
 */

public class MainActivity extends Activity{
    private static final String TAG = MainActivity.class.getSimpleName();
    ShowAdapter mShowAdapter;
    ArrayList<SectorEntity> sectorEntities = new ArrayList<>();
    ListView lvShow;
    ImageButton ibDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    public void doXY(){
        Picasso.get().load("").into(ibDo);
        String path = null;
        ImageView ivShow = null;
        Picasso.get().load(path).into(ivShow);

    }

    public void doPicasso(View view){
//        mShowAdapter = new ShowAdapter();
//        lvShow.setAdapter(mShowAdapter);

        startActivityForResult(new Intent(this, TestActivity.class), 0);
//        startActivity(new Intent(this, TestActivity.class));
    }

    private void initData(){
        for(int i=0;i<50;i++){
            SectorEntity entity = new SectorEntity(i+1, i+1);
            sectorEntities.add(entity);
        }
    }

    private void initViews(){
        lvShow = (ListView) findViewById(R.id.lv_show);
        ibDo = (ImageButton) findViewById(R.id.iv_do);
    }

    class ShowAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sectorEntities.size();
        }

        @Override
        public SectorEntity getItem(int position) {
            return sectorEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_show, null);

                mHolder = new ViewHolder();
                mHolder.tvShow = (TextView) convertView.findViewById(R.id.item_tv_show);
                convertView.setTag(mHolder);
            }else{
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.tvShow.setText("");

            TextLoader.getInstance().load(sectorEntities.get(position).getEnterpriseID(), sectorEntities.get(position).getSectorID(), mHolder.tvShow);

            return convertView;
        }

        class ViewHolder{
            TextView tvShow;
        }
    }
}
