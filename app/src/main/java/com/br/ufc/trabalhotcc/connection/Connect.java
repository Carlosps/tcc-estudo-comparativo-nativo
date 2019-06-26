package com.br.ufc.trabalhotcc.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.br.ufc.trabalhotcc.models.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class Connect {
    private static String url = "https://tcc-estudo-comparativo.firebaseio.com/pwa_features.json";
    private OkHttpClient client;

    public Connect(){
        client = new OkHttpClient();
    }

    public List<MenuItem> getItensFromServer() throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), new TypeToken<List<MenuItem>>(){}.getType());
        }
    }

    public List<MenuItem> getAllItens(Context c) throws IOException, ClassNotFoundException {
        List<MenuItem> lista = getItensFromLocal(c);
        if(lista!=null){
            return lista;
        }else{
            return getItensFromServer();
        }
    }

    public Bitmap getImageFromUrl(String url) throws IOException{
        Request request = new Request.Builder()
            .url(url)
            .build();

        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
    }

    public List<MenuItem> getItensFromLocal(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("APP", MODE_PRIVATE);
        String items = prefs.getString("items", null);

        Gson gson = new Gson();

        if (items != null) {
            return gson.fromJson(items, new TypeToken<List<MenuItem>>(){}.getType());
        }else{
            return null;
        }
    }

    public void saveItensToLocal(Context context, List<MenuItem> itens) {
        SharedPreferences.Editor editor = context.getSharedPreferences("APP", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        gson.toJson(itens);
        editor.putString("items", gson.toJson(itens));
        editor.commit();
    }
}
