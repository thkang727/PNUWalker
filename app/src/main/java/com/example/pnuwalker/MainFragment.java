
package com.example.pnuwalker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {

    TMapView frag_tMapView;
    TMapData frag_tMapData;
    RelativeLayout frag_mapLayout;

    TextView cur_time;
    long cur = System.currentTimeMillis();
    Date curDate = new Date(cur);
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String getTime = simpleDate.format(curDate);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        frag_mapLayout = view.findViewById(R.id.frag_map_layout);
        frag_tMapView = new TMapView(getActivity());
        frag_mapLayout.addView(frag_tMapView);
        initMap();

        cur_time = view.findViewById(R.id.frag_cur_time);
        cur_time.setText(getTime);

        return view;
    }

    private void initMap() {
        frag_tMapView.setSKTMapApiKey("l7xxdd90d676a9874f619cc8913aefc2bfe0"); //API 키 인증

        frag_tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        frag_tMapView.setZoomLevel(17); //지도 초기 확대수준 설정
        frag_tMapView.setSightVisible(true); //현재 보고있는 방향을 표시
        frag_tMapView.setIconVisibility(true); //현재 위치를 표시하는 파랑색 아이콘을 표기
        frag_tMapView.setLocationPoint(129.08102472195395, 35.23380020523731 ); //현재 위치 설정
        frag_tMapView.setCenterPoint(129.08102472195395, 35.23380020523731); // 지도 중심좌표 설정
    }

}
