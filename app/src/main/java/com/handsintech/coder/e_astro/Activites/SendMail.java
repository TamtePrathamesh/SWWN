package com.handsintech.coder.e_astro.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.handsintech.coder.e_astro.Activites.Config;
import com.handsintech.coder.e_astro.Activites.ForgotPassword;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;


    //Information to send email
    private String email;
    private String subject;
    private String message;
    public  String flag="";
    public ForgotPassword fp;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;
    //Class Constructor
    public SendMail(Context context, String email){
        //Initializing variables
        this.context = context;
        this.email = email;
        System.out.println(this.email);
        Log.d("emailcheck",email);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        fp=new ForgotPassword();
        Toast.makeText(context,"Mail Sent please check your mail box",Toast.LENGTH_LONG).show();
        flag="1";
//        Log.d("tra",fp.flag);


    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject("Hands In Tech");
            //Adding message
            mm.setText("<div> <p>click this link to change password-><br><a href='https://handintech.000webhostapp.com/NEW_HIT/user_valid_check.php?email="+email+"'>click here</a><p> </div>");

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
