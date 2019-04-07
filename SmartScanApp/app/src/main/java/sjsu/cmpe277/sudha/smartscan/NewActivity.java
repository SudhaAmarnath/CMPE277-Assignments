package sjsu.cmpe277.sudha.smartscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class NewActivity extends AppCompatActivity {

    ImageView imageView;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        imageView = findViewById(R.id.imageView3);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
                picturePath = extras.getString("path");
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu){
        getMenuInflater().inflate(R.menu.menu_scan,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan: imgToText();
                            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void imgToText() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();

        final SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

        if (textBlocks.size() != 0) {
            StringBuilder value = new StringBuilder();
                for (int i = 0; i < textBlocks.size(); ++i) {
                        TextBlock item = textBlocks.valueAt(i);
                        value.append(item.getValue());
                        value.append("\n");
                }
            Intent itext = new Intent(this,TextActivity.class);
            itext.putExtra("TEXT", (CharSequence) value);
            itext.putExtra("imgPath",picturePath);
            startActivity(itext);
        }
        else{
            Toast.makeText(this, "No Text found!", Toast.LENGTH_SHORT).show();
        }
    }
}
