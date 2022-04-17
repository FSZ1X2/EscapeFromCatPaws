package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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

import com.example.csi5175final.services.BackGroundMusic;

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
    private int picker1 = 0;
    private int picker2 = 0;
    private int picker3 = 0;
    private int picker_41 = 0;
    private int picker_42 = 0;
    //check which image is selected
    private int selected = 0;

    /**
     * function for generating the Type1 cat paw based on input colors.
     *
     * @param color1 first color extracted from user uploaded image.
     * @param color2 second color extracted from user uploaded image.
     * @param color3 third color extracted from user uploaded image.
     */
    private void generateType1(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype1);
        Bitmap bmp = ((BitmapDrawable) paw).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        //add color changes for type1 paws
        int[] colorIndex= {color1, color2, color3};
        Paint paint = new Paint();
        int colorID = new Random().nextInt(3);
        picker1= colorID;
        //add color
        paint.setColor(colorIndex[colorID]);
        int c1X = bmp.getWidth()/4;
        int c1Y = bmp.getHeight()/2;
        int c1R = bmp.getWidth()/3;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawCircle(c1X, c1Y, c1R, paint);
        //add source type of cat paws
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw1 = findViewById(R.id.image_preview1);
        paw1.setVisibility(View.VISIBLE);
        paw1.setImageBitmap(bmp);
    }

    /**
     * function for generating the Type2 cat paw based on input colors.
     *
     * @param color1 first color extracted from user uploaded image.
     * @param color2 second color extracted from user uploaded image.
     * @param color3 third color extracted from user uploaded image.
     */
    private void generateType2(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype2);
        Bitmap bmp = ((BitmapDrawable) paw).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        //add color changes for type2 paws
        int[] colorIndex= {color1, color2, color3};
        Paint paint = new Paint();
        int colorID = new Random().nextInt(3);
        picker2= colorID;
        paint.setColor(colorIndex[colorID]);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawRect(0,0,bmp.getWidth(),bmp.getHeight(), paint);
        //add source type of cat paws
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw2 = findViewById(R.id.image_preview2);
        paw2.setVisibility(View.VISIBLE);
        paw2.setImageBitmap(bmp);
    }

    /**
     * function for generating the Type3 cat paw based on input colors.
     *
     * @param color1 first color extracted from user uploaded image.
     * @param color2 second color extracted from user uploaded image.
     * @param color3 third color extracted from user uploaded image.
     */
    private void generateType3(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype3);
        Bitmap bmp = ((BitmapDrawable) paw).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        //add color changes for type3 paws
        int[] colorIndex= {color1, color2, color3};
        Paint paint = new Paint();
        int colorID = new Random().nextInt(3);
        picker3= colorID;
        paint.setColor(colorIndex[colorID]);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawRect(0,0,bmp.getWidth(),bmp.getHeight(), paint);
        //add source type of cat paws
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw3 = findViewById(R.id.image_preview3);
        paw3.setVisibility(View.VISIBLE);
        paw3.setImageBitmap(bmp);
    }

    /**
     * function for generating the Type4 cat paw based on input colors.
     *
     * @param color1 first color extracted from user uploaded image.
     * @param color2 second color extracted from user uploaded image.
     * @param color3 third color extracted from user uploaded image.
     */
    private void generateType4(int color1, int color2, int color3){
        Drawable paw = getDrawable(R.drawable.nekotype4);
        Bitmap bmp = ((BitmapDrawable) paw).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        //add first color
        paint.setColor(color1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawRect(0,0,bmp.getWidth(),bmp.getHeight(), paint);
        //add second color
        paint.setColor(color2);
        int c2X = bmp.getWidth()/2;
        int c2Y = 3 * bmp.getHeight()/4;
        int rand = new Random().nextInt(bmp.getHeight()/2);
        picker_41 = rand;
        int c2R = bmp.getHeight()/5 + rand;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawCircle(c2X, c2Y, c2R, paint);
        //add third color
        paint.setColor(color3);
        int c3X = 4 * bmp.getWidth()/5;
        int c3Y = bmp.getHeight()/4;
        int rand2 = new Random().nextInt(bmp.getHeight()/2);
        picker_42 = rand2;
        int c3R = bmp.getHeight()/5 + rand2;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawCircle(c3X, c3Y, c3R, paint);
        //add source type of cat paws
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bmp, 0, 0, paint);
        ImageView paw4 = findViewById(R.id.image_preview4);
        paw4.setVisibility(View.VISIBLE);
        paw4.setImageBitmap(bmp);
    }

    /**
     * function for generating cat paws based on input colors.
     *
     * @param color1 first color extracted from user uploaded image.
     * @param color2 second color extracted from user uploaded image.
     * @param color3 third color extracted from user uploaded image.
     */
    private void generateCatpaws(int color1, int color2, int color3){
        //generate new paws based on color
        generateType1(color1,color2,color3);
        generateType2(color1,color2,color3);
        generateType3(color1,color2,color3);
        generateType4(color1,color2,color3);
    }

    /**
     * function for get three colors from user uploaded image.
     *
     * @param bmp the image user selected and uploaded to the app.
     */
    @SuppressLint("ResourceAsColor")
    private void extraColor(Bitmap bmp){
        Palette p = Palette.from(bmp).generate();
        color1 = getTranslucentColor(0.5f, p.getDominantSwatch().getRgb());
        color2 = getTranslucentColor(0.5f, p.getVibrantColor(0x000000));
        color3 = getTranslucentColor(0.5f, p.getLightMutedColor(0x000000));
    }

    /**
     * function for add alpha value to color
     *
     * @param percent the alpha value
     * @param rgb the rgb color we have
     * @return the argb color for editing
     */
    protected int getTranslucentColor(float percent, int rgb) {
        int blue = Color.blue(rgb);
        int green = Color.green(rgb);
        int red = Color.red(rgb);
        int alpha = Color.alpha(rgb);

        alpha = Math.round(alpha*percent);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * function for opening a popup-window and let user select their action.
     *
     * @param view the current view of the editImage activity.
     */
    private void selectImage(View view){
        Intent getPictureIntent = new Intent(Intent.ACTION_PICK);
        getPictureIntent.setType("image/*");
        imagePickerActivityResult.launch(getPictureIntent);
    }

    /**
     * function for determining which cat paw user selected currently
     *
     * @param view the current view of the editImage activity.
     */
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

    /**
     * function for getting the image uri from the bitmap of user selected cat paw.
     *
     * @param inContext the current content resolver of the editImage activity.
     * @param inImage the current cat paw image user selected.
     */
    private Uri getImageUri(ContentResolver inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        //TODO: need to find a way to initial uniform title for images
        String path = MediaStore.Images.Media.insertImage(inContext, inImage, "paws", null);
        return Uri.parse(path);
    }

    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result != null && result.getData() !=null) {
                        //get user upload image
                        Uri imageUri = result.getData().getData();

                        //extract three colors from this image
                        Bitmap bitmap = null;
                        ContentResolver contentResolver = getContentResolver();

                        try {
                            if(Build.VERSION.SDK_INT < 28) {
                                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                                extraColor(bitmap);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                                bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true);
                                extraColor(bitmap);
                            }
//                            extraColor(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //show up redo button
                        Button redo = findViewById(R.id.redo);
                        redo.setEnabled(true);
                        //change instruction text
                        TextView intro = findViewById(R.id.intro);
                        intro.setText(R.string.edit_intro2);
                        System.out.println("color1" + color1);
                        System.out.println("color2" + color2);
                        System.out.println("color3" + color3);
                        generateCatpaws(color1,color2,color3);
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == -1){
                    Uri filePath = data.getData();
                    Intent i = new Intent(this, BackGroundMusic.class);
                    i.putExtra("command", "update" + filePath.toString());
                    this.startService(i);
                }
        }
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
                //open image upload popup window
                selectImage(view);
                //enable cat paw selections
                selectPaw(view);
            }
        });

        //submit user selected cat paw to server
        Button submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user id
                ContentResolver contentResolver = getContentResolver();
                @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(contentResolver,
                        Settings.Secure.ANDROID_ID);

                String result = "" + color1 + "," + color2 + "," + color3;
                if(selected == 1) result = result + ",1," + picker1 + ",0";
                if(selected == 2) result = result + ",2,"+ picker2+ ",0";
                if(selected == 3) result = result + ",3,"+ picker3+ ",0";
                if(selected == 4) result = result + ",4,"+ picker_41 + "," + + picker_42;
                //post to server
                try{
                    postImage(android_id, result);

                } catch (Exception e){
                    Toast.makeText(EditImage.this, "failed to save paw!", Toast.LENGTH_LONG);
                }
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
            case R.id.toggleMusic:
                Intent i = new Intent(this, BackGroundMusic.class);
                if(backgroundMusicOn){
                    i.putExtra("command", "stop");
                    this.startService(i);
                    backgroundMusicOn = false;
                } else{
                    i.putExtra("command", "start");
                    this.startService(i);
                    backgroundMusicOn = true;
                    Toast.makeText(this, R.string.music_start, Toast.LENGTH_LONG).show();
                }
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

    private void postImage(String id, String color){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = "https://heroic-arbor-303003.uc.r.appspot.com/images";
                    String urlParameters  = "id="+id+"&uri=" + color;
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
                        Toast.makeText(EditImage.this, "paw saved!", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    /**
     * Function to get stored color int value from DB. This function can be moved to the class where
     * we need to read the stored color value. Then call the same generateType method.
     * The value stored in DB is "[color1],[color2],[color3],[paw_type]". This will always get the latest stored color value.
     * */

}