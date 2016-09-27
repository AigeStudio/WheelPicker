package com.aigestudio.wheelpicker.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.model.City;
import com.aigestudio.wheelpicker.model.Province;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * WheelAreaPicker
 * Created by Administrator on 2016/9/14 0014.
 */
public class WheelAreaPicker extends LinearLayout implements IWheelAreaPicker {
    private static final float ITEM_TEXT_SIZE = 18;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    private static final int PROVINCE_INITIAL_INDEX = 0;

    private Context mContext;

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<String> mProvinceName, mCityName;

    private AssetManager mAssetManager;

    private LayoutParams mLayoutParams;

    private WheelPicker mWPProvince, mWPCity, mWPArea;

    public WheelAreaPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        initLayoutParams();

        initView(context);

        mProvinceList = getJsonDataFromAssets(mAssetManager);

        obtainProvinceData();

        addListenerToWheelPicker();
    }

    @SuppressWarnings("unchecked")
    private List<Province> getJsonDataFromAssets(AssetManager assetManager) {
        List<Province> provinceList = null;
        try {
            InputStream inputStream = assetManager.open("RegionJsonData.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            provinceList = (List<Province>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    private void initLayoutParams() {
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mContext = context;

        mAssetManager = mContext.getAssets();

        mProvinceName = new ArrayList<>();
        mCityName = new ArrayList<>();

        mWPProvince = new WheelPicker(context);
        mWPCity = new WheelPicker(context);
        mWPArea = new WheelPicker(context);

        initWheelPicker(mWPProvince, 1);
        initWheelPicker(mWPCity, 1.5f);
        initWheelPicker(mWPArea, 1.5f);
    }

    private void initWheelPicker(WheelPicker wheelPicker, float weight) {
        mLayoutParams.weight = weight;
        wheelPicker.setItemTextSize(dip2px(mContext, ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
        wheelPicker.setLayoutParams(mLayoutParams);
        addView(wheelPicker);
    }

    private void obtainProvinceData() {
        for (Province province : mProvinceList) {
            mProvinceName.add(province.getName());
        }
        mWPProvince.setData(mProvinceName);
        setCityAndAreaData(PROVINCE_INITIAL_INDEX);
    }

    private void addListenerToWheelPicker() {
        //监听省份的滑轮,根据省份的滑轮滑动的数据来设置市跟地区的滑轮数据
        mWPProvince.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                //获得该省所有城市的集合
                mCityList = mProvinceList.get(position).getCity();
                setCityAndAreaData(position);
            }
        });

        mWPCity.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                //获取城市对应的城区的名字
                mWPArea.setData(mCityList.get(position).getArea());
            }
        });
    }

    private void setCityAndAreaData(int position) {
        //获得该省所有城市的集合
        mCityList = mProvinceList.get(position).getCity();
        //获取所有city的名字
        //重置先前的城市集合数据
        mCityName.clear();
        for (City city : mCityList)
            mCityName.add(city.getName());
        mWPCity.setData(mCityName);
        mWPCity.setSelectedItemPosition(0);
        //获取第一个城市对应的城区的名字
        //重置先前的城区集合的数据
        mWPArea.setData(mCityList.get(0).getArea());
        mWPArea.setSelectedItemPosition(0);
    }

    @Override
    public String getProvince() {
        return mProvinceList.get(mWPProvince.getCurrentItemPosition()).getName();
    }

    @Override
    public String getCity() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getName();
    }

    @Override
    public String getArea() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getArea().get(mWPArea.getCurrentItemPosition());
    }

    @Override
    public void hideArea() {
        this.removeViewAt(2);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
