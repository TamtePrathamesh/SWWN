package com.handsintech.coder.e_astro;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codekidlabs.storagechooser.StorageChooser;
import com.handsintech.coder.e_astro.Activites.Home;
import com.handsintech.coder.e_astro.Activites.LoginActivity;
import com.handsintech.coder.e_astro.Activites.MainActivity;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;




public class AskExpert extends Fragment {

    String tag_string_req = "req_register",uid,question;
    TextView proname,selectedfilename;
   // Button btn_submit_query;
    SQLiteHandler db;

 //   ProgressBar p;
   // String urls_req="http://handintech.000webhostapp.com/NEW_HIT/upload.php";
   String urls_req="http://handsinservices.com/teachingApp/Api/Queadd.php";
   public ProgressDialog p;
    Intent intent;
    ImageView iv;
    private Button btn_submit_query,btnselectimage;
    private EditText editText;




    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 1234;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    StorageChooser chooser;

    public AskExpert() {
        // Required empty public constructor
    }

View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_ask_expert, container, false);


        Toolbar bar=Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        bar.setTitle("Ask the Expert");


//        Log.d("track",singleimg_url);
        iv=v.findViewById(R.id.pro_imageView2);
        db=new SQLiteHandler(getActivity());
        HashMap<String,String> user=db.getUserDetails();
        uid=user.get("uid");
        editText = (EditText)v.findViewById(R.id.editTextForQuery);
        proname=v.findViewById(R.id.pro_name_for_query);
        btn_submit_query=v.findViewById(R.id.buttonSubmitQuery);
        btnselectimage=v.findViewById(R.id.buttonChoose);
       p=new ProgressDialog(v.getContext());
       p.setCancelable(false);
       p.setMessage("Uploading....");

//p=new ProgressBar(getActivity());

        requestStoragePermission();


        selectedfilename=v.findViewById(R.id.selectedfilename);


        String pronametemp=this.getArguments().getString("pro");
        String img_url=this.getArguments().getString("pro_image");
        Picasso.with(getActivity()).load(img_url).into(iv);

      proname.setText(pronametemp);

        btn_submit_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (!p.isShowing())
                    p.show();
            if(filePath!=null){
            uploadMultipart();}
            else{newupload();}

            }
        });
        btnselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        });




        return  v;
    }




    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public String getPath(Uri uri) {





            Cursor cursor = this.getContext().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = this.getContext().getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

            return path;


    }

    //method to show file chooser
    private void showFileChooser() {
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult(intent,  PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("req_code",String.valueOf(requestCode));
        Log.d("req_res",String.valueOf(resultCode));
        Log.d("req_data",String.valueOf(data));

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
          filePath = data.getData();


//
                try {
                    String tempfilename=PathUtil.getPath(getActivity(),filePath);
                    File f=new File(tempfilename);
                    String namess=f.getName();
                    selectedfilename.setText(namess);

                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                  //  imageView.setImageBitmap(bitmap);
                    //Toast.makeText(getActivity(),"File selected", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

        }


    }

    public void uploadMultipart() {
        //getting name for the image


        //getting the actual path of the image
        String path = getPath(filePath);
        question=editText.getText().toString().trim();
        //Uploading code
        try {

                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(getActivity(), uploadId, urls_req)
                        .addFileToUpload(path, "imageorvideo") //Adding file
                        .addParameter("userId", uid)
                        .addParameter("quetion", question)//Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

           // if (p.isShowing())
                p.dismiss();

            } catch(Exception exc){
                Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }

    }


public void newupload()
{
        question=editText.getText().toString().trim();
        String uploadId = UUID.randomUUID().toString();

        //Creating a multi part request
//        new MultipartUploadRequest(getActivity(), uploadId, urls_req)
//                .addParameter("userId", "13_no")
//                .addParameter("quetion", "???_no")//Adding text parameter to the request
//                .setNotificationConfig(new UploadNotificationConfig())
//                .setMaxRetries(2)
//                .startUpload();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urls_req,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("e1", "Register Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("success");
                    if (error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        if (p.isShowing())
                            p.dismiss();
                        Toasty.success(getActivity(), "done", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // if (p.isShowing())
                    p.dismiss();
                Log.e("e1", "Registration Error: " + error.getMessage());
                Toasty.error(getActivity(),"check your network connection",
                        Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", uid);
                params.put("quetion", question);


                return params;
            }

        };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        }
    }




    

