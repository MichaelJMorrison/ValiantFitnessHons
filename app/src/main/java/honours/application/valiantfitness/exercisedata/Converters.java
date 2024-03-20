package honours.application.valiantfitness.exercisedata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;

import kotlin.UByteArray;

//https://developer.android.com/training/data-storage/room/referencing-data
public class Converters {
@TypeConverter
public static Date fromTimestamp(Long value) {
    return value == null ? null : new Date(value);
}

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

  //  https://stackoverflow.com/questions/57117262/how-to-save-images-to-room-persistence-library
@TypeConverter
public static Bitmap toBitMap(byte[] bytes){
return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
}


@TypeConverter
public static byte[] fromBitMap(Bitmap bitmap){

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
    return outputStream.toByteArray();

}

}
