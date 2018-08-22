package com.test.android00cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CartlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CtAdapter adapter;
    private Button play_again;
    private Button payment;
    private ArrayList<CartlistVO> list = new ArrayList<>();
    private static final int DIALOG_PLAY_AGAIN_YESNO = 1;
    private static final int DIALOG_PAYMENT = 2;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);
        recyclerView = findViewById(R.id.rView);
        play_again = findViewById(R.id.play_again);
        payment = findViewById(R.id.payment);

        new Thread(){
            @Override
            public void run() {
                jsonNetWork();
            }
        }.start();
    }

    void jsonNetWork(){
        String path = "http://192.168.0.115:8090/cart/ctcu_json_selectAll.do";
        String imgPath = "";
        for(int i=0;i<4;i++){
            imgPath += path.split("/")[i];
            imgPath += "/";
        }
        imgPath += "resources/uploadimg/";
        HttpURLConnection conn = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            URL url = new URL(path);
            Log.i("testLog",url.toString());
            conn = (HttpURLConnection) url.openConnection();
            Log.i("testLog", "conn.getResponseCode(): "+conn.getResponseCode());
            Log.i("testLog", "conn.getContentType(): "+conn.getContentType());
            Log.i("testLog", "conn.getContentLength(): "+conn.getContentLength());
            Log.i("testLog", "conn.getResponseMessage(): "+conn.getResponseMessage());

            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String str = "";
            StringBuilder sb = new StringBuilder();
            while((str = br.readLine()) != null){
                sb.append(str);
            }
            Log.i("testLog",sb.toString());
            JSONArray array = null;
            try {
                array = new JSONArray(sb.toString());
                for(int i=0; i < array.length();i++){
                    CartlistVO vo = new CartlistVO();
                    JSONObject obj = array.getJSONObject(i);
                    vo.setNum("  "+String.valueOf(obj.getInt("num"))+"    ");
                    vo.setName("  "+obj.getString("name")+"    ");
                    vo.setPrice("  "+obj.getString("price"));
                    String imgName = imgPath;
                    imgName += obj.getString("imgName");
                    vo.setImgUrl(imgName);
                    vo.setCtCuCheck(obj.getString("ctCuCheck"));
                    Log.i("testLog",obj.getString("num"));
                    Log.i("testLog",obj.getString("name"));
                    Log.i("testLog",obj.getString("price"));
                    Log.i("testLog",imgName);
                    Log.i("testLog",obj.getString("ctCuCheck"));
                    list.add(vo);
                }

                play_again.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DIALOG_PLAY_AGAIN_YESNO);
                    }
                });

                payment.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DIALOG_PAYMENT);
                    }
                });

                adapter = new  CtAdapter(list, this);
                layoutManager = new LinearLayoutManager(this);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });

            } catch (JSONException e) {
                Log.e("testLog",e.getMessage());
            }
        } catch (MalformedURLException e) {
            Log.e("testLog",e.getMessage());
        } catch (IOException e) {
            Log.e("testLog",e.getMessage());
        } finally{
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if(isr!=null){
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e){
                }
            }

            if(conn!=null){
                conn.disconnect();
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PLAY_AGAIN_YESNO:
                return new AlertDialog.Builder(CartlistActivity.this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle(R.string.alert_dialog_game)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(
                                        getApplicationContext(),
                                        MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).create();
            case DIALOG_PAYMENT:
                return new AlertDialog.Builder(CartlistActivity.this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle(R.string.alert_payment)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(
                                        getApplicationContext(),
                                        MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }
        return null;
    }
}
