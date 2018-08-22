package com.test.android00cart;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private SensorViewEx sensorView;
    private int winWidth;
    private int winHeight;
    private int total;
    private int price;
    private static final int DIALOG_DELETE_YESNO = 1;
    private static final int DIALOG_GO_CARTLIST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        sensorView = new SensorViewEx(this);

        Button btn_end = (Button) findViewById(R.id.endButton);
        btn_end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DELETE_YESNO);
            }
        });

        final Button cartlist = (Button) findViewById(R.id.cartlist);
        cartlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_GO_CARTLIST);
            }
        });

        container.addView(sensorView);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        WindowManager wm =
                (WindowManager) getSystemService(WINDOW_SERVICE);
        Display mDisplay = wm.getDefaultDisplay();
        winWidth = mDisplay.getWidth();
        winHeight = mDisplay.getHeight();
        List<Sensor> sensors =
                sensorManager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensorManager.registerListener(
                    (SensorEventListener) this,
                    sensors.get(0),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }//end onCreate

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DELETE_YESNO:
                return new AlertDialog.Builder(MainActivity.this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle(R.string.alert_dialog_delete)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
            case DIALOG_GO_CARTLIST:
                return new AlertDialog.Builder(MainActivity.this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle(R.string.alert_dialog_cartlist)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(
                                        getApplicationContext(), CartlistActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_keep, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }
        return null;
    }


    //start of SensorView
    class SensorViewEx extends SurfaceView
            implements SurfaceHolder.Callback, ValueAnimator.AnimatorUpdateListener {

        private static final int LAMP_SIZE = 50;
        private Bitmap lamp;
        private Bitmap lamp1, lamp2, lamp3, lamp4, lamp5, lamp6, lamp7;
        private int w;
        private int h;
        private float x;
        private float y;
        private CustomViewThread customViewThread;
        private String str = "하늘에서 신상이 내린다면";
        private String strball = "신상";
        private Bitmap lamp1_small, lamp2_small, lamp3_small,
                lamp4_small, lamp5_small, lamp6_small, lamp7_small;
        int x1, y1, x2, y2, x3, y3, x4, y4, x5, y5, x6, y6, x7, y7;


        public SensorViewEx(Context c) {
            super(c);

            //비트맵 생성, 이미지파일 소스
            lamp = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.thecart_2);
            lamp1 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.gdshouse);
            lamp2 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.coupon10000);
            lamp3 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.clothes);
            lamp4 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.hpnotebook);
            lamp5 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.coupon5000);
            lamp6 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.bono);
            lamp7 = BitmapFactory.decodeResource(
                    c.getResources(),R.mipmap.banana);

            x1 = 25;
            x2 = 125;
            x3 = 225;
            x4 = 325;
            x5 = 425;
            x6 = 525;
            x7 = 625;

            //각 아이템들 고정 x값 좌표

            y1 = -150;
            y2 = -200;
            y3 = -400;
            y4 = -500;
            y5 = -800;
            y6 = -2000;
            y7 = -1000;

            //각 아이템들 고정 y값 시작 좌표

            getHolder().addCallback(this);
            customViewThread =
                    new CustomViewThread(getHolder(), this);
            setFocusable(true);

        }// end constructor

        //카트 좌우 이동
        public void move(float mx, float my) {
            this.x -= (mx * 4f);
            //카트 y값 좌표
            this.y = (my = 800.0f);

            if (this.x < 35.0f) {
                this.x = 35.0f;
            } else if (this.x > winWidth - 35) {
                this.x = winWidth - 35;
            } else if ((this.x + LAMP_SIZE) > this.w) {
                this.x = this.w - LAMP_SIZE;
            }
            invalidate();

        }// end move

        @Override
        protected void onSizeChanged(
                int w, int h, int oldw, int oldh) {
            this.w = w;
            this.h = h;
        }

        @Override
        public void onDraw(Canvas canvas) {
            float screenXCenter1 = winWidth - 650f;
            float screenYCenter = winHeight / 1.5f;
            float screenXCenter2 = winWidth - 550f;
            float screenXCenter3 = winWidth - 450f;
            float screenXCenter4 = winWidth - 350f;
            float screenXCenter5 = winWidth - 250f;
            float screenXCenter6 = winWidth - 150f;
            float screenXCenter7 = winWidth - 50f;
            //x 센서 기준 좌표
            float radius = 50.0f;
            float lampXHalf = x - (lamp.getWidth() / 2);
            float lampYHalf = y - (lamp.getHeight() / 2);

            Rect ourRect = new Rect();
            ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight() / 2);

            //각 아이템들 떨어지는 속도
            y1 += 2;
            y2 += 3;
            y3 += 4;
            y4 += 4;
            y5 += 5;
            y6 += 2;
            y7 += 4;

            //각 아이템들 y값 이상 될시 시작좌표로 이동
            if (y1 > 800) {y1 = -100;}
            if (y2 > 800) {y2 = -200;}
            if (y3 > 800) {y3 = -150;}
            if (y4 > 800) {y4 = -100;}
            if (y5 > 800) {y5 = -200;}
            if (y6 > 800) {y6 = -300;}
            if (y7 > 800) {y7 = -100;}
            if (y2 > 800) {y2 = -200;}

            Log.i("testLog", "y1:" + y1);

            //비트맵 이미지들 사이즈 줄이기
            lamp1_small = Bitmap.createScaledBitmap(lamp1, 100, 100, true);
            lamp2_small = Bitmap.createScaledBitmap(lamp2, 100, 100, true);
            lamp3_small = Bitmap.createScaledBitmap(lamp3, 100, 100, true);
            lamp4_small = Bitmap.createScaledBitmap(lamp4, 100, 100, true);
            lamp5_small = Bitmap.createScaledBitmap(lamp5, 100, 100, true);
            lamp6_small = Bitmap.createScaledBitmap(lamp6, 100, 100, true);
            lamp7_small = Bitmap.createScaledBitmap(lamp7, 100, 100, true);

            canvas.drawColor(Color.parseColor("#000000"));
            //아이템들 비트맵 그려주기
            canvas.drawBitmap(lamp1_small, x1, y1, null);
            canvas.drawBitmap(lamp2_small, x2, y2, null);
            canvas.drawBitmap(lamp3_small, x3, y3, null);
            canvas.drawBitmap(lamp4_small, x4, y4, null);
            canvas.drawBitmap(lamp5_small, x5, y5, null);
            canvas.drawBitmap(lamp6_small, x6, y6, null);
            canvas.drawBitmap(lamp7_small, x7, y7, null);

            canvas.drawBitmap(lamp, lampXHalf, lampYHalf, null);

            Paint text = new Paint();
            text.setARGB(200, 255, 255, 255);
            //화면에 나오는 글자들 사이즈,각 좌표
            text.setTextSize(30);
            canvas.drawText(str, 50, 30, text);
            canvas.drawText(strball, 50, 80, text);


            //각 아이템들 좌표안에 들어올시 이벤트
            if ((this.x > (screenXCenter1 - 50)
                    && this.x < (screenXCenter1 + 50))) {
                //센서좌표,범위
                str = "GD 신발";
                price = 199000;
                strball = "가격:" + price;
                y1 += 5;

                if (
                        y1 > (screenYCenter)
                                && y1 < (screenYCenter + 5)
                                && y1 > 100
                        ) {
                    total += price;
                    str = "장바구니 담기 완료 - GD 신발";
                    Log.i("testLog", "total1:" + total);

                }
            } else if ((this.x > (screenXCenter2 - 50)
                    && this.x < (screenXCenter2 + 50))
                    && y2 > 100
                    ) {
                str = "10000원 할인";
                price = -10000;
                strball = "할인:" + price;
                if (
                        y2 > (screenYCenter)
                                && y2 < (screenYCenter + 2)
                                && total >1
                        ) {
                    total += price;
                    str = "장바구니 담기 완료 - 10000원 할인";
                    Log.i("testLog", "total2:" + total);

                }
            } else if ((this.x > (screenXCenter3 - 50)
                    && this.x < (screenXCenter3 + 50))
                    && y3 > 100
                    ) {
                str = "남자 봄옷 세트";
                price = 100000;
                strball = "가격:" + price;
                if (
                        y3 > (screenYCenter)
                                && y3 < (screenYCenter + 2)) {
                    total += price;
                    str = "장바구니 담기 완료 - 남자 봄옷";
                    Log.i("testLog", "total3:" + total);
                }
            } else if ((this.x > (screenXCenter4 - 50)
                    && this.x < (screenXCenter4 + 50))
                    && y4 >100
                    ) {
                str = "KOSTA 노트북";
                price = 1440000;
                strball = "가격:" + price;
                if (
                        y4 > (screenYCenter)
                                && y4 < (screenYCenter + 5)) {
                    total += price;
                    str = "장바구니 담기 완료 - KOSTA 노트북";
                    Log.i("testLog", "total4:" + total);
                }
            } else if ((this.x > (screenXCenter5 - 50)
                    && this.x < (screenXCenter5 + 50))
                    && y5 > 100
                    ) {
                str = "5000원 할인 쿠폰";
                price = -5000;
                strball = "할인:" + price;
                y5 -= 2;
                if (
                        y5 > (screenYCenter)
                                && y5 < (screenYCenter + 2)
                                && total > 50000
                        ) {
                    total += price;
                    str = "장바구니 담기 완료 - 5000원 할인";
                    Log.i("testLog", "total5:" + total);
                }
            } else if ((this.x > (screenXCenter6 - 50)
                    && this.x < (screenXCenter6 + 50))
                    && y6 > 100
                    ) {
                str = "보노보노";
//                price = 1;
//                strball = "가격:"+price;
                if (
                        y6 > (screenYCenter)
                                && y6 < (screenYCenter + 2)) {
//                    total += price;
                    total = 0;
                    str = "장바구니 담기 완료 - 보노보노";
                    Log.i("testLog", "total6:" + total);
                }
            } else if ((this.x > (screenXCenter7 - 50)
                    && this.x < (screenXCenter7 + 50))
                    && y7 >100
                    ) {
                str = "바나나";
                price = 1000;
                strball = "가격:" + price;
                if (
                        y7 > (screenYCenter)
                                && y7 < (screenYCenter + 5)) {
                    total += price;
                    str = "장바구니 담기 완료 - 바나나";
                    Log.i("testLog", "total7:" + total);
                }

            } else {
                str = "쇼핑중";
                strball = "신상 가격";
            }

            String totalView = "합계:" + total;
            canvas.drawText(totalView, 500, 50, text);

            invalidate();

        }// end onDraw


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            customViewThread.setRunning(true);
            customViewThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean result = true;
            customViewThread.setRunning(false);
            while (result) {
                try {
                    customViewThread.join();
                    result = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }//end SensorViewEx class

    class CustomViewThread extends Thread {

        private SurfaceHolder surfaceHolder;
        private SensorViewEx customView;
        private boolean running = false;

        public void setRunning(boolean running) {
            this.running = running;
        }

        public CustomViewThread(
                SurfaceHolder surfaceHolder,
                SensorViewEx customView) {
            super();
            this.surfaceHolder = surfaceHolder;
            this.customView = customView;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        customView.onDraw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }//end CustomViewThread class

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    public void onAccuracyChanged(
            Sensor sensor, int accuracy) {
    }// end onAccuracyChanged

    @Override
    public void onSensorChanged(
            final SensorEvent event) {
        Sensor sensor = event.sensor;
        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                sensorView.move(
                        event.values[0], event.values[1]);

                break;
            default:
                break;
        }
    }// end onSensorChanged

    @Override
    protected void onStop() {
        super.onStop();
    }

}


