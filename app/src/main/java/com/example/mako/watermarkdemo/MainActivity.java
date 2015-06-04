package com.example.mako.watermarkdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {


//    private WatermarkView wv;
    private SingleTouchView iv;
    private LinearLayout ll;
    private Button btn;
    private ImageView showimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        wv = (WatermarkView) findViewById(R.id.wv);
//
//        wv.setText("云雾没有山");
//        wv.setTextSize(16);
//        wv.setBackgroundResId(R.drawable.ic_watermark2);

        iv= (SingleTouchView) findViewById(R.id.iv);
        ll= (LinearLayout) findViewById(R.id.ll);
        btn= (Button) findViewById(R.id.btn);
        showimg= (ImageView) findViewById(R.id.showimg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv.setEditable(false);

                Bitmap bitmap1=loadBitmapFromView(ll,ll.getWidth(),ll.getHeight());



                Log.i("MainActivity", "bitmap1" + bitmap1);

                if (bitmap1!=null){
                    showimg.setImageBitmap(bitmap1);

                    iv.setEditable(true);
                }

            }
        });

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_watermark2);

        WatermarkDrawable drawable=new WatermarkDrawable(this,"云雾山没有山",44,bitmap,300,300);

        iv.setImageDrawable(drawable);

    }

    /**
     * 将view转成bitmap
     * @return
     */
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
