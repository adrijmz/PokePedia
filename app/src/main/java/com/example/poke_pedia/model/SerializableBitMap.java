package com.example.poke_pedia.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableBitMap implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Bitmap bitmap;

    public SerializableBitMap(Bitmap imagePokemon) {
        this.bitmap = imagePokemon;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            BitmapDataObject bitmapDataObject = new BitmapDataObject();
            bitmapDataObject.imageByteArray = stream.toByteArray();
            out.writeObject(bitmapDataObject);
        }
    }

    /**
     * Included for serialization - read this object from the supplied input
     * stream.
     */
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        BitmapDataObject bitmapDataObject = (BitmapDataObject) in.readObject();
        if (bitmapDataObject != null) {
            bitmap = BitmapFactory.decodeByteArray(
                    bitmapDataObject.imageByteArray, 0,
                    bitmapDataObject.imageByteArray.length);
        }
    }

    protected class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        public byte[] imageByteArray;
    }

    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}