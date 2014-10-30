package tw.yes.v.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularImageView extends ImageView {

    Paint paint;

    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), false);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // paint contains the shader that will texture the shape
        paint.setShader(shader);

        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        float radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;
        canvas.drawRoundRect(rect, radius, radius, paint);


    }
}
