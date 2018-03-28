package com.soundcloud.android.crop;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
/**
 * Created by User on 29.03.2018.
 */

public class RotationCropper extends Cropper {

    private CropImageViewWithRotation imageView;

    public RotationCropper(RotateBitmap rotateBitmap, CropImageViewWithRotation imageView, Handler handler,
                           OnHilightViewReady onHilightViewReady) {
        super(rotateBitmap, imageView, handler, onHilightViewReady);
        this.imageView = imageView;
    }


    @Override
    protected void makeDefault(int aspectX, int aspectY) {
        if (rotateBitmap == null) {
            return;
        }

        HighlightView hv = new HighlightView(imageView);
        int width = rotateBitmap.getWidth();
        int height = rotateBitmap.getHeight();
        Rect imageRect = new Rect(0, 0, width, height);
        float minSide = Math.min(width, height);
        int cropWidth;
        int cropHeight;
        if (imageView.getDegreesRotated() == 90 || imageView.getDegreesRotated() == 270) {
            cropHeight = (int) minSide;
            cropWidth = (int) (minSide * aspectX / aspectY);

        } else {
            cropWidth = (int) minSide;
            cropHeight = (int) (minSide * aspectY / aspectX);
        }
        int x = (width - cropWidth) / 2;
        int y = (height - cropHeight) / 2;
        RectF cropRect = new RectF((float) x, (float) y, (float) (x + cropWidth), (float) (y + cropHeight));
        hv.setup(imageView.getUnrotatedMatrix(), imageRect, cropRect, aspectX != 0 && aspectY != 0);
        imageView.add(hv);
    }
}
