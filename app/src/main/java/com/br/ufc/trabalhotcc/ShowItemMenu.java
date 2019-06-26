package com.br.ufc.trabalhotcc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.ufc.trabalhotcc.models.MenuItem;

public class ShowItemMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView img = findViewById(R.id.imageView);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        MenuItem item = (MenuItem) getIntent().getExtras().getSerializable("item");

        byte[] decodedString = Base64.decode(item.getImageFile(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        img.setImageBitmap(decodedByte);
        title.setText(item.getTitle());
        description.setText(item.getDescription());
    }
}
