package com.mazbaz.tamalcoolique;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.widget.ImageView;


import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mazbaz.tamalcoolique.requests.Item;
import com.mazbaz.tamalcoolique.requests.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {
    private static OkHttpClient httpClient;

    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed to load image Utils l:46");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                SVG svg = null;
                try {
                    svg = SVG.getFromInputStream(stream);
                } catch (SVGParseException e) {
                    e.printStackTrace();
                }
                stream.close();

                if (svg != null) {
                    final Picture picture = svg.renderToPicture();
                    Bitmap bitmap = Bitmap.createBitmap(
                            picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawPicture(picture);

                    // Mettre à jour l'ImageView sur le thread principal
                    target.post(new Runnable() {
                        @Override
                        public void run() {
                            target.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }

    private static final String PREF_NAME = "AppDatas";

    public static void storeData(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void removeData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
