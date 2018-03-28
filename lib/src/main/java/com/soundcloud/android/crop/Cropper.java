package com.soundcloud.android.crop;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by User on 29.03.2018.
 */

public class Cropper {

    protected RotateBitmap rotateBitmap;
    protected CropImageView imageView;
    protected HighlightView cropView;
    protected Handler handler;
    protected OnHilightViewReady onHilightViewReady;

    public Cropper(RotateBitmap rotateBitmap, CropImageView imageView, Handler handler,
                   OnHilightViewReady onHilightViewReady) {
        this.rotateBitmap = rotateBitmap;
        this.imageView = imageView;
        this.handler = handler;
        this.onHilightViewReady = onHilightViewReady;
    }

    protected void makeDefault(int aspectX, int aspectY) {
        if (rotateBitmap == null) {
            return;
        }

        HighlightView hv = new HighlightView(imageView);
        final int width = rotateBitmap.getWidth();
        final int height = rotateBitmap.getHeight();

        Rect imageRect = new Rect(0, 0, width, height);

        // Make the default size about 4/5 of the width or height
        int cropWidth = Math.min(width, height) * 4 / 5;
        @SuppressWarnings("SuspiciousNameCombination")
        int cropHeight = cropWidth;

        if (aspectX != 0 && aspectY != 0) {
            if (aspectX > aspectY) {
                cropHeight = cropWidth * aspectY / aspectX;
            } else {
                cropWidth = cropHeight * aspectX / aspectY;
            }
        }

        int x = (width - cropWidth) / 2;
        int y = (height - cropHeight) / 2;

        RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
        hv.setup(imageView.getUnrotatedMatrix(), imageRect, cropRect, aspectX != 0 && aspectY != 0);
        imageView.add(hv);
    }

    public void crop(final int aspectX, int aspectY) {
        handler.post(new Runnable() {
            public void run() {
                makeDefault(aspectX,aspectX);
                imageView.invalidate();
                if (imageView.highlightViews.size() == 1) {
                    cropView = imageView.highlightViews.get(0);
                    cropView.setFocus(true);
                    if (onHilightViewReady != null)
                        onHilightViewReady.setHilightView(cropView);
                }
            }
        });
    }

    public interface OnHilightViewReady {
        void setHilightView(HighlightView hilightView);
    }
}
