
package com.example.pnuwalker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;

public class MainFragment extends Fragment {

    TMapView frag_tMapView;
    TMapData frag_tMapData;
    RelativeLayout frag_mapLayout;

    //현재시각 표시.
    TextView cur_time;
    Date curDate = new Date();
    SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    long  tmpnow =  curDate.getTime() ;
    String get_time = nowDate.format(tmpnow);

    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmm");
    TextView next_time;

    // 나중에 시간표에 저장된 DB를 가져 올 것이므로, 함수 구상용도이고, 임의로 시간순 작성.
    String[] today_schedule = new String[]{"컴알","고토","도서관자습"};
    String[] today_schedule_time = new String[]{"202104081900","202104082100","202104082230"};
    String[] today_schedule_site = new String[]{"제도관","인문관","새벽벌"};
    double[] today_schedule_site_xpoint = new double[]{129.082112,129.08134602,129.081379771};
    double[] today_schedule_site_ypoint = new double[]{35.231154,35.232287960304575,35.23577906709686};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        //티맵 표시.
        frag_mapLayout = view.findViewById(R.id.frag_map_layout);
        frag_tMapView = new TMapView(getActivity());
        frag_mapLayout.addView(frag_tMapView);
        initMap();

        //현재시각 표시.
        cur_time = view.findViewById(R.id.frag_cur_time);
        cur_time.setText(get_time);

        //이전-현재-다음 일정 표기.
        int i = 0;
        while(i < today_schedule_time.length){
            Date tmp_time = null;
            try {
                tmp_time = simpleDate.parse(today_schedule_time[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                curDate = simpleDate.parse(simpleDate.format(curDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(tmp_time.getTime() > curDate.getTime() ){
                makeMaker(today_schedule_site[i],today_schedule_site_xpoint[i],today_schedule_site_ypoint[i]);
                TextView next_schedule = view.findViewById(R.id.will_do_time);
                next_schedule.setText(today_schedule[i]);
                TextView next_site = view.findViewById(R.id.will_do_site);
                next_site.setText(today_schedule_site[i]);
                if(i > 0) {
                    TextView now_schedule = view.findViewById(R.id.do_time);
                    now_schedule.setText(today_schedule[i-1]);
                    TextView now_site = view.findViewById(R.id.do_site);
                    now_site.setText(today_schedule_site[i-1]);
                    if(i > 1){
                        TextView bef_schedule = view.findViewById(R.id.did_time);
                        bef_schedule.setText(today_schedule[i-2]);
                        TextView bef_site = view.findViewById(R.id.did_site);
                        bef_site.setText(today_schedule_site[i-2]);
                    }
                }
                break;
            }
            i++;
        }

        //다음일과까지 남은 시간 표기.
        Date d1 = null;
        try {
            d1 = simpleDate.parse(today_schedule_time[i]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            curDate = simpleDate.parse(simpleDate.format(curDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        long diff =  (d1.getTime() - curDate.getTime()) -32400000;
        String get_next_time = f.format(diff);
        next_time = view.findViewById(R.id.time_to_next);
        next_time.setText(get_next_time);

        return view;
    }

    private void initMap() {
        frag_tMapView.setSKTMapApiKey("l7xxdd90d676a9874f619cc8913aefc2bfe0"); //API 키 인증

        frag_tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        frag_tMapView.setZoomLevel(17); //지도 초기 확대수준 설정
        frag_tMapView.setSightVisible(true); //현재 보고있는 방향을 표시
        frag_tMapView.setIconVisibility(true); //현재 위치를 표시하는 파랑색 아이콘을 표기
        frag_tMapView.setLocationPoint(129.082112, 35.231154 ); //현재 위치 설정
        frag_tMapView.setCenterPoint(129.082112, 35.231154); // 지도 중심좌표 설정
    }

    private void makeMaker(String mark_site, double mark_x, double mark_y){

        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        TMapPoint tMapPoint1 = new TMapPoint(mark_y, mark_x); // 마커 좌표.

        // 마커 아이콘
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_r_m_a);
        markerItem1.setIcon(bitmap); // 마커 아이콘 지정
        markerItem1.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem1.setTMapPoint( tMapPoint1 ); // 마커의 좌표 지정
        markerItem1.setName(mark_site); // 마커의 타이틀 지정

        frag_tMapView.addMarkerItem("markerItem1", markerItem1); // 지도에 마커 추가

        frag_tMapView.setCenterPoint( mark_x, mark_y );
    }

}
