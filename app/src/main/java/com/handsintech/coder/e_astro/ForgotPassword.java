package com.handsintech.coder.e_astro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.Session;

public class ForgotPassword extends AppCompatActivity {

    EditText entermailforgot;
    Button sendmailforgot;
    public  String email="";
    ProgressDialog pd;
    Session mSession=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();
        pd=new ProgressDialog(this);

        entermailforgot=findViewById(R.id.editTextEmailForgot);
        sendmailforgot=findViewById(R.id.buttonSendmailforgot);

        sendmailforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              email=entermailforgot.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter your mail", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                   entermailforgot.setError("Enter valid email");
                }
                else
                {
////                    new SendMail().execute("");
//                    Properties p=new Properties();
//                    p.put("mail.smtp.host","smtp.gmail.com");
//                    p.put("mail.smtp.socketFactory.port","465");
//                    p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//                    p.put("mail.smtp.auth","true");
//                    p.put("mail.smtp.port","456");
//
//
//                    mSession=Session.getDefaultInstance(p, new Authenticator() {
//                        @Override
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication("from@gmail.com","psw");
//                        }
//                    });
//
//                    pd.setTitle("Sending mail");
//                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    pd.setCancelable(false);
                    SendMail sm = new SendMail(ForgotPassword.this, email);

                    //Executing sendmail to send email
                    sm.execute();
                }

            }
        });
    }
//    class RetriveFeedbackTesk extends AsyncTask<String,String,Void>
//    {
//        @Override
//        protected Void doInBackground(String... strings) {
//                try
//                {
//          Message m=new MimeMessage(mSession);
//
//
//                }
//                catch (MessagingException e)
//                {
//                e.printStackTrace();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                return  null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }

//    private class SendMail extends AsyncTask<String, Integer, Void> {
//
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = ProgressDialog.show(ForgotPassword.this, "Please wait", "Sending mail", true, false);
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
//        }
//
//        protected Void doInBackground(String... params) {
//            Mail m = new Mail("tamteprathamesh@gmail.com", "prathamesh9402");
//
//            String[] toArr = {email, "tamteprathamesh@gmail.com"};
//            m.setTo(toArr);
//            m.setFrom("tamteprathamesh@gmail.com");
//            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
//            m.setBody("Email body.");
//
//            try {
//                if(m.send()) {
//                    Toast.makeText(ForgotPassword.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(ForgotPassword.this, "Email was not sent.", Toast.LENGTH_LONG).show();
//                }
//            } catch(Exception e) {
//                Log.e("MailApp", "Could not send email", e);
//            }
//            return null;
//        }
//    }




}

