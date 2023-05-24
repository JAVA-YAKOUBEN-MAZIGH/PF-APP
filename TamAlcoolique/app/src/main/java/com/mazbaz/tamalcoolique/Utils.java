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
                System.out.println("Failed to load image Utils l:34");
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

    public void test() {
        String jsonResponse = "{\n" +
                "\t\"id\": 1,\n" +
                "\t\"username\": \"mazbaz\",\n" +
                "\t\"email\": \"mail@mail.com\",\n" +
                "\t\"provider\": \"local\",\n" +
                "\t\"confirmed\": true,\n" +
                "\t\"blocked\": false,\n" +
                "\t\"createdAt\": \"2023-05-22T16:09:27.259Z\",\n" +
                "\t\"updatedAt\": \"2023-05-22T16:16:57.126Z\",\n" +
                "\t\"alcohol_level\": null,\n" +
                "\t\"hunger_level\": null,\n" +
                "\t\"urine_level\": null,\n" +
                "\t\"role\": {\n" +
                "\t\t\"id\": 2,\n" +
                "\t\t\"name\": \"Public\",\n" +
                "\t\t\"description\": \"Default role given to unauthenticated user.\",\n" +
                "\t\t\"type\": \"public\",\n" +
                "\t\t\"createdAt\": \"2023-05-21T21:38:28.497Z\",\n" +
                "\t\t\"updatedAt\": \"2023-05-21T21:38:28.497Z\"\n" +
                "\t},\n" +
                "\t\"items\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 2,\n" +
                "\t\t\t\"name\": \"testpackage\",\n" +
                "\t\t\t\"createdAt\": \"2023-05-21T22:33:31.028Z\",\n" +
                "\t\t\t\"updatedAt\": \"2023-05-21T22:33:31.728Z\",\n" +
                "\t\t\t\"publishedAt\": \"2023-05-21T22:33:31.725Z\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"price\": null,\n" +
                "\t\t\t\"alcohol_level\": null,\n" +
                "\t\t\t\"food_level\": null,\n" +
                "\t\t\t\"urine_level\": null,\n" +
                "\t\t\t\"money\": null,\n" +
                "\t\t\t\"buyable\": null\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 3,\n" +
                "\t\t\t\"name\": \"pro2\",\n" +
                "\t\t\t\"createdAt\": \"2023-05-21T22:41:06.411Z\",\n" +
                "\t\t\t\"updatedAt\": \"2023-05-21T22:41:08.477Z\",\n" +
                "\t\t\t\"publishedAt\": \"2023-05-21T22:41:08.475Z\",\n" +
                "\t\t\t\"description\": \"qsd\",\n" +
                "\t\t\t\"price\": null,\n" +
                "\t\t\t\"alcohol_level\": null,\n" +
                "\t\t\t\"food_level\": null,\n" +
                "\t\t\t\"urine_level\": null,\n" +
                "\t\t\t\"money\": null,\n" +
                "\t\t\t\"buyable\": null\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Gson gson = new Gson();
        User user = gson.fromJson(jsonResponse, User.class);
        // Utilisation des données de l'utilisateur
        System.out.println("ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        // ...

        // Utilisation des articles de l'utilisateur
        List<Item> items = user.getItems();
        for (Item item : items) {
            System.out.println("Item ID: " + item.getId());
            System.out.println("Item Name: " + item.getName());
        }
    }
}
