package com.example.csi5175final;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditImage extends AppCompatActivity {

    //color value from the image
    private int color1 = R.color.black;
    private int color2 = R.color.light_blue_400;
    private int color3 = R.color.white;
    //check which image is selected
    private int selected = 0;

    private void generateType1(int color1, int color2, int color3){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.nekotype1);
        //add color changes for type1 paws
        int[] colorIndex= {color1, color2, color3};
        Bitmap newPaws = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newPaws);
        Paint paint = new Paint();
        int colorID = new Random().nextInt(3);
        //add color
        Paint colorBrush = new Paint();
        colorBrush.setColor(colorIndex[colorID]);
        int c1X = bmp.getWidth()/2;
        int c1Y = 0;
        int c1R = bmp.getHeight()/2;
        canvas.drawCircle(c1X, c1Y, c1R, colorBrush);
        colorBrush.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        //add source type of cat paws
        canvas.drawBitmap(bmp, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw1 = findViewById(R.id.image_preview1);
        paw1.setVisibility(View.VISIBLE);
        paw1.setImageBitmap(bmp);
    }

    private void generateType2(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype2);
        //add color changes for type2 paws
        int[] colorIndex= {color1, color2, color3};
        int colorID = new Random().nextInt(3);
        paw.setColorFilter(colorIndex[colorID], PorterDuff.Mode.SRC_ATOP);
        ImageView paw2 = findViewById(R.id.image_preview2);
        paw2.setImageDrawable(paw);
        paw2.setVisibility(View.VISIBLE);
    }

    private void generateType3(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype3);
        //add color changes for type3 paws
        int[] colorIndex= {color1, color2, color3};
        int colorID = new Random().nextInt(3);
        paw.setColorFilter(colorIndex[colorID], PorterDuff.Mode.SRC_ATOP);
        ImageView paw3 = findViewById(R.id.image_preview3);
        paw3.setImageDrawable(paw);
        paw3.setVisibility(View.VISIBLE);
    }

    private void generateType4(int color1, int color2, int color3){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.nekotype4);
        //add color changes for type4 paws
        Bitmap newPaws = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newPaws);
        Paint paint = new Paint();
        //add first color
        canvas.drawColor(color1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        //add second color
        Paint colorBrush = new Paint();
        colorBrush.setColor(color2);
        int c2X = new Random().nextInt(bmp.getWidth());
        int c2Y = new Random().nextInt(bmp.getHeight());
        int c2R = new Random().nextInt(bmp.getWidth()/2);
        canvas.drawCircle(c2X, c2Y, c2R, colorBrush);
        colorBrush.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bmp, 0, 0, paint);
        //add third color
        colorBrush.setColor(color3);
        int c3X = new Random().nextInt(bmp.getWidth());
        int c3Y = new Random().nextInt(bmp.getHeight());
        int c3R = new Random().nextInt(bmp.getWidth()/2);
        canvas.drawCircle(c3X, c3Y, c3R, colorBrush);
        colorBrush.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bmp, 0, 0, paint);
        //add source type of cat paws
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw4 = findViewById(R.id.image_preview4);
        paw4.setVisibility(View.VISIBLE);
        paw4.setImageBitmap(bmp);
    }

    private void generateCatpaws(int color1, int color2, int color3){
        //generate new paws based on color
        generateType1(color1,color2,color3);
        generateType2(color1,color2,color3);
        generateType3(color1,color2,color3);
        generateType4(color1,color2,color3);
    }

    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result != null) {
                        //get user upload image
                        Uri imageUri = result.getData().getData();

                        //randomly generate three cat paws from this image
                        Random random = new Random();
                        Bitmap bitmap = null;
                        ContentResolver contentResolver = getContentResolver();

                        //TODO: find way to get random color from image

                        try {
                            if(Build.VERSION.SDK_INT < 28) {
                                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                                bitmap = ImageDecoder.decodeBitmap(source);
                            }
//                            Palette palette = Palette.generate(bitmap);
//                            color1 = palette.getLightVibrantSwatch().getRgb();
//                            color2 = palette.getVibrantSwatch().getRgb();
//                            color3 = palette.getDarkVibrantSwatch().getRgb();
//                            List<Palette.Swatch> colors = builder.generate().getSwatches();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //show up redo button
                        Button redo = findViewById(R.id.redo);
                        redo.setEnabled(true);
                        //change instruction text
                        TextView intro = findViewById(R.id.intro);
                        intro.setText(R.string.edit_intro2);
                        generateCatpaws(color1,color2,color3);
                    }
                }
            }
    );

    private void selectImage(View view){
        //initialize a new layout inflater instance for uploading
        LayoutInflater resultPage = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View select_view = resultPage.inflate(R.layout.selecting_image,null);
        //initialize a popup window instance
        PopupWindow popupWindow = new PopupWindow(select_view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //get image from user album
        Button bt_album = select_view.findViewById(R.id.gallery);
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getPictureIntent = new Intent(Intent.ACTION_PICK);
                getPictureIntent.setType("image/*");
                //TODO: debug - why if not select goes wrong
                imagePickerActivityResult.launch(getPictureIntent);
                //close popup
                popupWindow.dismiss();
            }
        });
        //get image from user camera
        Button bt_camera = select_view.findViewById(R.id.camera);
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: debug - why doesn't work
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagePickerActivityResult.launch(takePictureIntent);
                //close popup
                popupWindow.dismiss();
            }
        });
        //back to previous page
        Button bt_back = select_view.findViewById(R.id.go_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void selectPaw(View view){
        //get submit button from view
        Button submit = findViewById(R.id.submit_button);
        //allow user to select one cat paw
        ImageView paw1 = findViewById(R.id.image_preview1);
        ImageView paw2 = findViewById(R.id.image_preview2);
        ImageView paw3 = findViewById(R.id.image_preview3);
        ImageView paw4 = findViewById(R.id.image_preview4);
        //reset to non-selection
        paw1.setAlpha(0.3f);
        paw2.setAlpha(0.3f);
        paw3.setAlpha(0.3f);
        paw4.setAlpha(0.3f);
        //when select one, change state
        paw1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                paw1.setAlpha(1.0f);
                paw2.setAlpha(0.3f);
                paw3.setAlpha(0.3f);
                paw4.setAlpha(0.3f);
                //enable submit
                submit.setEnabled(true);
                //change select image
                selected = 1;
            }
        });
        paw2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                paw1.setAlpha(0.3f);
                paw2.setAlpha(1.0f);
                paw3.setAlpha(0.3f);
                paw4.setAlpha(0.3f);
                //enable submit
                submit.setEnabled(true);
                //change select image
                selected = 2;
            }
        });
        paw3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                paw1.setAlpha(0.3f);
                paw2.setAlpha(0.3f);
                paw3.setAlpha(1.0f);
                paw4.setAlpha(0.3f);
                //enable submit
                submit.setEnabled(true);
                //change select image
                selected = 3;
            }
        });
        paw4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                paw1.setAlpha(0.3f);
                paw2.setAlpha(0.3f);
                paw3.setAlpha(0.3f);
                paw4.setAlpha(1.0f);
                //enable submit
                submit.setEnabled(true);
                //change select image
                selected = 4;
            }
        });
    }

    private Uri getImageUri(ContentResolver inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext, inImage, "paws", null);
        return Uri.parse(path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);

        //handle re-generate paws
        Button redo = findViewById(R.id.redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //re-generate cat paws
                generateCatpaws(color1,color2,color3);

            }
        });

        //upload custom image
        Button upload = findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user id
                ContentResolver contentResolver = getContentResolver();
                @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(contentResolver,
                        Settings.Secure.ANDROID_ID);
                //get selected image uri
                //allow user to select one cat paw
                ImageView paw1 = findViewById(R.id.image_preview1);
                ImageView paw2 = findViewById(R.id.image_preview2);
                ImageView paw3 = findViewById(R.id.image_preview3);
                ImageView paw4 = findViewById(R.id.image_preview4);
                Bitmap bmp = null;
                if(selected == 1) bmp = ((BitmapDrawable)paw1.getDrawable()).getBitmap();
                if(selected == 2) bmp = ((BitmapDrawable)paw1.getDrawable()).getBitmap();
                if(selected == 3) bmp = ((BitmapDrawable)paw1.getDrawable()).getBitmap();
                if(selected == 4) bmp = ((BitmapDrawable)paw1.getDrawable()).getBitmap();
                Uri submitImage = getImageUri(contentResolver, bmp);
                //post to server
                postImage(android_id, submitImage.toString());
            }
        });

        //submit user selected cat paw to server
        Button submit = findViewById(R.id.upload_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open image upload popup window
                selectImage(view);
                //enable cat paw selections
                selectPaw(view);
            }
        });

        //back to lobby
        Button back = findViewById(R.id.leave_edit_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditImage.this, HomePage.class));
            }
        });
    }

    //setup menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // back to homepage setting
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    //setup actions for each selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.toggleMusic: //TODO: add toggle music feature

                return true;
            case R.id.backHome: //back to lobby
                startActivity(new Intent(EditImage.this, HomePage.class));
                return true;
            case R.id.quitGame: // close the app
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postImage(String id, String url){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = "HTTP://18.191.10.52:3000/score";
                    String urlParameters  = "id="+id+"&url=" + url;
                    InputStream stream = null;
                    byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                    int postDataLength = postData.length;
                    URL urlObj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);

                    conn.connect();

                    try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        wr.write( postData );
                        wr.flush();
                    }

                    stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                    String result = reader.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditImage.this, R.string.post_score, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void getImage(String id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String results = "";
                try{
                    String url = "HTTP://18.191.10.52:3000/image?id=" + id;
                    InputStream stream = null;
                    URL urlObj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoInput(true);
                    conn.setRequestMethod("GET");


                    conn.connect();

                    stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                    results = reader.readLine();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                String finalResults = results;
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String formatted = finalResults.replace("[", "").replace("]", "");
                        Uri uri = Uri.parse(formatted);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditImage.this.getContentResolver(), uri);
                            //TODO:update image with the bitmap.
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(EditImage.this,R.string.no_img_found,Toast.LENGTH_LONG);
                        }


                    }
                });
            }
        });
    }
}