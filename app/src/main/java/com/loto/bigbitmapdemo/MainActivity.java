package com.loto.bigbitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);

        Log.d(TAG, "width: " + metrics.widthPixels);
        Log.d(TAG, "height: " + metrics.heightPixels);
        Log.d(TAG, "density: " + metrics.density);
        Log.d(TAG, "densityDpi: " + metrics.densityDpi);

        ImageView imageView = findViewById(R.id.image);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bbbbb, null);

        imageView.setImageBitmap(decodePhoto(1000, 1000));
//        imageView.setImageBitmap(bitmap);
//        imageView.setImageResource(R.drawable.bbbbb);
    }

    /**
     * 从指定路径加载一个 Bitmap, 并限定其最大宽高。
     * 如果文件的宽高大于给定的最大宽高，则会等比例缩放到刚好满足要求。
     *
     * @author 郑海鹏
     */
    @Nullable
    public Bitmap decodePhoto(int maxWidth, int maxHeight) {
        // 先获取原始照片的宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bbbbb, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        Log.d(TAG, "srcWidth: " + srcWidth);
        Log.d(TAG, "srcHeight: " + srcHeight);

        // 计算是否需要缩放
        float ratioW = 1F * srcWidth / maxWidth;
        float ratioH = 1F * srcHeight / maxHeight;
        float ratio = Math.max(ratioW, ratioH);

        // 尺寸没有超过最大尺寸时，不用缩放
        if (ratio < 1) {
            return BitmapFactory.decodeResource(getResources(), R.drawable.bbbbb);
        }

        // 目标宽度
        int dstWidth = (int) (srcWidth / ratio);

        // 大于4倍时, 先使用 inSampleSize 缩放到目标尺寸的2-4倍
        int inSampleSize = ratio >= 4 ? (int) (ratio / 2) : 1;
        inSampleSize = (int) Math.pow(2, (int) (Math.log(inSampleSize) / Math.log(2)));

        // 再使用 inDensity 精确缩放剩余的部分
        options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        options.inScaled = true;
//        options.inDensity = srcWidth;
//        options.inTargetDensity = dstWidth * inSampleSize;
        return BitmapFactory.decodeResource(getResources(), R.drawable.bbbbb, options);
    }
}