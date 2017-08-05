package com.example.coolweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wzj on 2017/8/5.
 */

public class AreaFragment extends Fragment {

    private final int LEVEL_PROVINCE = 0;
    private final int LEVEL_CITY = 1;
    private final int LEVEL_COUNTY = 2;

    private TextView title_text;
    private Button btn_back;
    private ListView listView;

    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<County> countyList = new ArrayList<>();
    private ArrayList<String> dataList = new ArrayList<>();

    private Province selectedProvince ;
    private City selectedCity ;
    private County selectedCounty ;

    private int CURRENT_LEVEL;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progerssDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area,container,false);
        title_text = view.findViewById(R.id.title_text);
        btn_back = view.findViewById(R.id.btn_back);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadProvinceDatas();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CURRENT_LEVEL == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(i);
                    loadCityDatas();
                }else if(CURRENT_LEVEL == LEVEL_CITY){
                    selectedCity = cityList.get(i);
                    loadCountyDatas();
                }else if (CURRENT_LEVEL == LEVEL_COUNTY){
                    loadWeather();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_LEVEL == LEVEL_PROVINCE){

                }else if(CURRENT_LEVEL == LEVEL_CITY){
                    loadProvinceDatas();
                }else if (CURRENT_LEVEL == LEVEL_COUNTY){
                    loadCityDatas();
                }
            }
        });

    }

    private void loadProvinceDatas() {
        title_text.setText("中国");
        btn_back.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
            for(int i=0;i<provinceList.size();i++){
                dataList.add(provinceList.get(i).getProvinceName());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CURRENT_LEVEL = LEVEL_PROVINCE;
        }else{
            String  address = "http://guolin.tech/api/china";
            loadFromInter(address,"Province");
        }
    }

    private void loadFromInter(String  address, final String type) {
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respondText = response.body().string();
                boolean result =false;
                if("Province".equals(type)){
                    result = Utility.handleProvinceResponse(respondText);
                }else if("City".equals(type)){
                    result = Utility.handleCityRespond(respondText,selectedProvince.getId());
                }else if("County".equals(type)){
                    result = Utility.handleCountyRespond(respondText,selectedCity.getId());
                }

                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("Province".equals(type)){
                                loadProvinceDatas();
                            }else if("City".equals(type)){
                                loadCityDatas();
                            }else if ("County".equals(type)){
                                loadCountyDatas();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        if(progerssDialog == null){
            progerssDialog = new ProgressDialog(getActivity());
            progerssDialog.setMessage("正在加载...");
            progerssDialog.setCanceledOnTouchOutside(true);
        }
        progerssDialog.show();
    }

    private void closeProgressDialog(){
        if(progerssDialog !=null){
            progerssDialog.dismiss();
        }
    }

    private void loadWeather() {

    }

    private void loadCountyDatas() {

        title_text.setText(selectedCity.getCityName());
        btn_back.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            dataList.clear();
            for(int i=0;i<countyList.size();i++){
                dataList.add(countyList.get(i).getCountyName());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CURRENT_LEVEL = LEVEL_COUNTY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String  address = "http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            loadFromInter(address,"County");
        }
    }

    private void loadCityDatas() {
        title_text.setText(selectedProvince.getProvinceName());
        btn_back.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(int i=0;i<cityList.size();i++){
                dataList.add(cityList.get(i).getCityName());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CURRENT_LEVEL = LEVEL_CITY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            String  address = "http://guolin.tech/api/china/"+provinceCode;
            loadFromInter(address,"City");
        }

    }
}
