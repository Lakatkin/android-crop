package com.soundcloud.android.crop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by User on 29.03.2018.
 */

public class CropWithRotation extends Crop {

    public static Crop of(Uri source, Uri destination) {
        return new CropWithRotation(source, destination);
    }


    private CropWithRotation(Uri source, Uri destination) {
        super(source, destination);
    }

    @Override
    public Intent getIntent(Context context) {
        cropIntent.setClass(context, RotateCropImageActivity.class);
        return cropIntent;
    }


}
