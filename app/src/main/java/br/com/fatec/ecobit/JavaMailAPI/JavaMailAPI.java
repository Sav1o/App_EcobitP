package br.com.fatec.ecobit.JavaMailAPI;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI {

    private Context mContext;
    private Session mSession;

    private String mEmail;
    private String mSubject;
    private String mMessage;

    private ProgressBar mProgressBar;

    // Constructor
    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage, ProgressBar mProgressBar) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
        this.mProgressBar = mProgressBar;
    }

    public void sendMail() {
        // Show ProgressBar while sending email
        mProgressBar.setVisibility(View.VISIBLE);

        // ExecutorService to run the task in the background
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create properties and session
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    mSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                        }
                    });

                    // Creating MimeMessage object
                    MimeMessage mm = new MimeMessage(mSession);
                    mm.setFrom(new InternetAddress(Utils.EMAIL));
                    mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
                    mm.setSubject(mSubject);
                    mm.setText(mMessage);

                    // Send email
                    Transport.send(mm);

                    // Update UI after sending email
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Message Sent", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (MessagingException e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
