package sjsu.cmpe277.sudha.smartscan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextActivity extends AppCompatActivity {

    public static int count=1;
    TextView textview;
    String textt,imgPath;
    TabLayout tabLayout;
    File pdfFile,docFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textt = extras.getString("TEXT");
            imgPath = extras.getString("imgPath");
        }
        textview = findViewById(R.id.textView3);
        textview.setText(textt);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Intent i = new Intent(TextActivity.this,HomeActivity.class);
                switch (tab.getPosition()) {
                    case 0:
                        saveDocFile();
                        Toast.makeText(TextActivity.this, "File SAVED!", Toast.LENGTH_SHORT).show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        break;
                    case 1:
                        savePdfFile();
                        Toast.makeText(TextActivity.this, "File SAVED!", Toast.LENGTH_SHORT).show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        break;
                    case 2:
                        copyText();
                        break;
                    case 3:
                        shareText();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Intent i = new Intent(TextActivity.this,HomeActivity.class);
                switch (tab.getPosition()) {
                    case 0:
                        saveDocFile();
                        Toast.makeText(TextActivity.this, "File SAVED!", Toast.LENGTH_SHORT).show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        break;
                    case 1:
                        savePdfFile();
                        Toast.makeText(TextActivity.this, "File SAVED!", Toast.LENGTH_SHORT).show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        break;
                    case 2:
                        copyText();
                        break;
                    case 3:
                        shareText();
                        break;
                }
            }
        });
    }

    public void saveDocFile(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String fileName = "DOC_" + formatter.format(now) + ".doc";

        try
        {
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+"SmartScan", "Scanned Files");
            if (!root.exists())
            {
                root.mkdirs();
            }
            docFile = new File(root, fileName);

            FileWriter writer = new FileWriter(docFile,true);
            writer.append(textt.toString()+"\n\n");
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }

    public void copyText(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Text Copied", textt);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied Text", Toast.LENGTH_SHORT).show();
    }

    public void shareText(){

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textt);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

     public void savePdfFile(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String fileName = "DOC_" + formatter.format(now) + ".pdf";
        try
        {
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+"SmartScan", "Scanned Files");
            if (!root.exists())
            {
                root.mkdirs();
            }
            pdfFile = new File(root, fileName);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            document.add(new Paragraph(textt));
            document.close();
            Log.d("OK", "done");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}