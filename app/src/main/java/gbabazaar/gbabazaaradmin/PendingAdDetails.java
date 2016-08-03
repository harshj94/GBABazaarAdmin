package gbabazaar.gbabazaaradmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import rebus.permissionutils.AskagainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class PendingAdDetails extends AppCompatActivity {
    TextView title, category, rate, description, name, city, allow, edit, save, dontallow;
    ParseQuery<ParseObject> parseQuery;
    String objectId;
    ParseObject parseObject;
    byte[][] bytes;
    LinearLayout linearLayout;
    ProgressDialog csprogress;
    int images, i;
    ImageView imageView;
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_ad_details);
        cat = getIntent().getStringExtra("category");
        objectId = getIntent().getStringExtra("objectId");
        linearLayout = (LinearLayout) findViewById(R.id.place);
        imageView = (ImageView) findViewById(R.id.back);
        edit = (TextView) findViewById(R.id.edit);
        save = (TextView) findViewById(R.id.save);
        edit.setText("Ad View");
        save.setText("");
        new FetchAdDetails().execute();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        PermissionManager.with(PendingAdDetails.this)
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(1);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                    }
                })
                .ask();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    private class FetchAdDetails extends AsyncTask<Void, Void, Void> {
        Boolean result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            csprogress = new ProgressDialog(PendingAdDetails.this);
            csprogress.setCancelable(false);
            csprogress.setMessage("Please wait...");
            csprogress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
            result = connectionDetector.isConnectingToInternet();
            if (result) {
                parseQuery = ParseQuery.getQuery(cat);
                parseQuery.orderByDescending("createdAt");
                try {
                    parseObject = parseQuery.get(objectId);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                images = parseObject.getInt("Images");
                int j;
                bytes = new byte[images][];
                for (j = 0; j < images; j++) {
                    ParseFile parseFile = parseObject.getParseFile("image" + j + "");
                    try {
                        bytes[j] = parseFile.getData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            csprogress.dismiss();
            if (!result) {
                new AlertDialog.Builder(PendingAdDetails.this)
                        .setTitle("Internet Connection Error")
                        .setCancelable(false)
                        .setMessage("It seems as if you are not connected to internet")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new FetchAdDetails().execute();
                            }
                        })
                        .show();
            } else {
                title = (TextView) findViewById(R.id.title);
                category = (TextView) findViewById(R.id.category);
                rate = (TextView) findViewById(R.id.rate);
                description = (TextView) findViewById(R.id.description);
                name = (TextView) findViewById(R.id.name);
                city = (TextView) findViewById(R.id.city);
                allow = (TextView) findViewById(R.id.allow);
                dontallow = (TextView) findViewById(R.id.dontallow);
                title.setText("Title: " + parseObject.getString("Title"));
                category.setText("Category: " + parseObject.getString("Category"));
                rate.setText("Rate: " + parseObject.getString("Rate"));
                description.setText("Description: " + parseObject.getString("Description"));
                name.setText("Name: " + parseObject.getString("Name"));
                city.setText("City: " + parseObject.getString("City"));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.NO_GRAVITY;
                linearLayout.setLayoutParams(lp);
                linearLayout.removeAllViews();
                for (i = 0; i < images; i++) {
                    final ImageView imageView = new ImageView(PendingAdDetails.this);
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes[i], 0, bytes[i].length);
                    bmp = Bitmap.createScaledBitmap(bmp, 250, 400, false);
                    imageView.setPadding(4, 2, 4, 2);
                    imageView.setImageBitmap(bmp);
                    imageView.setId(i);
                    linearLayout.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Time now = new Time();
                            now.setToNow();
                            String s = now.toString();
                            s = s.substring(0, Math.min(s.length(), 15));
                            File f = new File(Environment.getExternalStorageDirectory() + File.separator + s);
                            try {
                                f.createNewFile();
                                FileOutputStream fo = new FileOutputStream(f);
                                fo.write(bytes[imageView.getId()]);
                                fo.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Uri path = Uri.fromFile(f);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "image/*");
                            startActivity(intent);
                        }
                    });
                }

                allow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parseObject.put("Status", "accepted");
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Toast.makeText(PendingAdDetails.this, "Ad has been accepted.", Toast.LENGTH_SHORT).show();
                                Pending.st = true;
                                Visible.st = true;
                                ParseQuery<ParseObject> parseQuery1 = new ParseQuery<>("GameScore");
                                parseQuery1.getInBackground("ajCfInBYGZ", new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.increment(cat);
                                        object.saveInBackground();
                                    }
                                });
                                finish();
                            }
                        });
                    }
                });
                dontallow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parseObject.put("Status", "rejected");
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Toast.makeText(PendingAdDetails.this, "Ad has been rejected.", Toast.LENGTH_SHORT).show();
                                Pending.st = true;
                                Deleted.st = true;
                                finish();
                            }
                        });
                    }
                });
            }
        }
    }
}
