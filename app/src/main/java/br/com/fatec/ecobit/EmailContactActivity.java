package br.com.fatec.ecobit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.fatec.ecobit.JavaMailAPI.JavaMailAPI;

public class EmailContactActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mSubject;
    private EditText mMessage;
    private Button btnSend;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_email);


        mEmail = findViewById(R.id.mailID);
        mSubject = findViewById(R.id.subjectID);
        mMessage = findViewById(R.id.messageID);
        btnSend = findViewById(R.id.btnSend);
        mProgressBar = findViewById(R.id.progressBar);

        mEmail.setText("savio.a.nsoares.smurf@gmail.com");
        mEmail.setEnabled(false);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String mail = mEmail.getText().toString().trim();
        String message = mMessage.getText().toString();
        String subject = mSubject.getText().toString().trim();

        // Enviar e-mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mail, subject, message, mProgressBar);
        javaMailAPI.sendMail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
