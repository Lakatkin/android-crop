package com.soundcloud.android.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by User on 29.03.2018.
 */

public class CropImageViewWithRotation extends CropImageView {


    private RotateBitmap rotateBitmap;
    private int degreesRotated;

    public CropImageViewWithRotation(Context context) {
        super(context);
    }

    public CropImageViewWithRotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CropImageViewWithRotation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void rotate(int degrees) {
        if (degrees < 0) {
            degrees = (degrees % 360) + 360;
        } else {
            degrees = degrees % 360;
        }
        degreesRotated = (degreesRotated + degrees) % 360;
        rotateBitmap.setRotation(degreesRotated);
        this.center();
        this.highlightViews.clear();
    }

    @Override
    public void setImageRotateBitmapResetBase(final RotateBitmap bitmap, final boolean resetSupp) {
        this.rotateBitmap = bitmap;
        this.degreesRotated = bitmap.getRotation();
        super.setImageRotateBitmapResetBase(bitmap, resetSupp);
    }

    public int getDegreesRotated() {
        return degreesRotated;
    }
}
