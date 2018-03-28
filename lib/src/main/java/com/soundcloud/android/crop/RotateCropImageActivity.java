package com.soundcloud.android.crop;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by User on 29.03.2018.
 */

public class RotateCropImageActivity extends CropImageActivity {

    CropImageViewWithRotation cropImageViewWithRotation;
    private View leftRotationButton;
    private View rightRotationButton;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        initUI();
    }

    private void initUI() {
        cropImageViewWithRotation = (CropImageViewWithRotation) findViewById(R.id.crop_image);
        leftRotationButton = findViewById(R.id.crop_rotate_left_button);
        rightRotationButton = findViewById(R.id.crop_rotate_right_button);
        if (leftRotationButton != null)
            leftRotationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cropImageViewWithRotation.rotate(-90);
                    startCrop();
                }
            });
        if (rightRotationButton != null)
            rightRotationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cropImageViewWithRotation.rotate(90);
                    startCrop();
                }
            });
    }

    protected int getLayoutId() {
        return R.layout.crop__activity_rotation_crop;
    }

    @Override
    protected Cropper getCropper(RotateBitmap rotateBitmap, CropImageView imageView, Handler handler,
                                 Cropper.OnHilightViewReady onHilightViewReady) {
        return new RotationCropper(rotateBitmap, (CropImageViewWithRotation) imageView,
                handler, onHilightViewReady);
    }

    private void checkForRemoveOriginal() {
        boolean needRemove = getIntent().getBooleanExtra(Crop.Extra.REMOVE_ORIGINAL_KEY, false);
        if (needRemove) {
            Uri input = getIntent().getData();
            CropUtil.getFromMediaUri(this, this.getContentResolver(), input).delete();
        }
    }

    @Override
    protected Bitmap prepareBitmapToSave(int outWidth, int outHeight, Rect r) {
        setExifRotation(cropImageViewWithRotation.getDegreesRotated());
        Bitmap croppedImage;
        try {
            if (cropImageViewWithRotation.getDegreesRotated() == 90 || cropImageViewWithRotation.getDegreesRotated() == 270)
                croppedImage = this.decodeRegionCrop(r, outHeight, outWidth);
            else
                croppedImage = this.decodeRegionCrop(r, outWidth, outHeight);
        } catch (IllegalArgumentException var8) {
            this.setResultException(var8);
            return null;
        }
        if (croppedImage != null) {
            cropImageViewWithRotation.center();
            cropImageViewWithRotation.highlightViews.clear();
        }
        return croppedImage;
    }

    @Override
    protected void saveOutput(Bitmap croppedImage) {
        RotateBitmap rotateBitmap = new RotateBitmap(croppedImage, getExifRotation());
        final Bitmap rotatedImage = Bitmap.createBitmap(croppedImage, 0, 0,
                croppedImage.getWidth(), croppedImage.getHeight(), rotateBitmap.getRotateMatrix(), false);/**/
        super.saveOutput(rotatedImage);
    }

    @Override
    protected void startClearAfterSave(){
            checkForRemoveOriginal();
            cropImageViewWithRotation.clear();
    }

    @Override
    protected void saveRotation(){

    }
}
