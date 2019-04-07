package sjsu.cmpe277.sudha.smartscan;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;



public class HomeActivity extends AppCompatActivity {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public  static final int RequestPermissionCode  = 2 ;
    private int selectedFiles =0;
    private int flag=0;
    Toolbar toolBar;
    SearchManager searchManager;
    SearchView searchView;
    FloatingActionButton fab;
    TabLayout tb;
    TextView tv2;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) !=PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED )
            EnableRuntimePermission();

        tv2 = findViewById(R.id.textView2);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener(){
            public void onClick (View v){
                selectImage();
            }
        });
        setSupportActionBar(toolBar);
        tb = findViewById(R.id.tabLayout2);
        tb.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(HomeActivity.this, "Sharing...", Toast.LENGTH_SHORT).show();
                        //saveDocFile();
                        break;
                    case 1:
                        Toast.makeText(HomeActivity.this, "Deleting...", Toast.LENGTH_SHORT).show();
                        //savePdfFile();
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this, "Renaming...", Toast.LENGTH_SHORT).show();
                        //copyText();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(HomeActivity.this, "Sharing...", Toast.LENGTH_SHORT).show();
                        //saveDocFile();
                        break;
                    case 1:
                        Toast.makeText(HomeActivity.this, "Deleting...", Toast.LENGTH_SHORT).show();
                        //savePdfFile();
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this, "Renaming...", Toast.LENGTH_SHORT).show();
                        //copyText();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu){
        return true;
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    private void selectImage() {
        final CharSequence[] items = { "From Camera", "From Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("From Camera")) {
                    ImagePicker.cameraOnly()
                             .imageDirectory("../SmartScan/images")
                             .start(HomeActivity.this);
                }
                else if (items[item].equals("From Gallery")) {
                    ImagePicker.create(HomeActivity.this)
                            .single()
                            .showCamera(false)
                            .folderMode(true)
                            .toolbarFolderTitle("Folders")
                            .toolbarImageTitle("Tap to select")
                            .start();
                }
            }
        });
        builder.show();
    }

    private void EnableRuntimePermission(){
        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{
                CAMERA,WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    public void showAllFiles(){
        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);
        tv2.setText(dbHandler.loadHandler());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);

            CropImage.activity(Uri.fromFile(new File(image.getPath())))
                    .start(this);
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent i = new Intent(this, NewActivity.class);
                i.putExtra("path",resultUri.getPath());
                startActivity(i);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(flag==1){
            flag=0;
            tb.setVisibility(View.GONE);
            fab.show();
            toolBar.getMenu().clear();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            toolBar.setBackgroundDrawable(new ColorDrawable(0xff3f51b5));
            HomeActivity.this.invalidateOptionsMenu();
        }
        else{
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeActivity.this,"Permission Granted.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(HomeActivity.this,"Permission Canceled.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}
