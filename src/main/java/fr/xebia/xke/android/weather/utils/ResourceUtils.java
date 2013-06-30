package fr.xebia.xke.android.weather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import fr.xebia.xke.android.weather.R;

/**
 * Created by mounirboudraa on 28/05/13.
 */

public final class ResourceUtils {


    public enum ResourceType {

        DRAWABLE("drawable"), VIEW("id"), LAYOUT("layout"), STRING("string");

        private String type;

        private ResourceType(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }
    }

    public static int getResourceIdentifierByName(final Context context, ResourceType resType, final String name) {
        final String formattedName = name.replace("-", "_");
        return context.getResources().getIdentifier(formattedName, resType.getValue(), context.getPackageName());
    }

    public static Bitmap rotateDrawable(Context context, int drawableId, double angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle - 180);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }


}
