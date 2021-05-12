package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Model.UserItem;
import com.example.capstoneprojectfinalize.Model.UserModelTable;

public class ReportBugActivity extends AppCompatActivity {
    Button buttonReportSend, buttonReportCancel;
    EditText editReportSubject, editReportBody;
    UserItem mUserItem;
    TextView emailToAddressText;

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);

        Intent i = getIntent();
        final Integer currentId = i.getIntExtra( "user_id_key",0);
        final String currentName = i.getStringExtra("user_name_key");
        final Integer currentAccess = i.getIntExtra("user_access_key",0);
        final String currentEmail = i.getStringExtra("user_email_key");

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);

        buttonReportSend = findViewById(R.id.button18);
        buttonReportCancel = findViewById(R.id.button19);
        editReportBody = findViewById(R.id.editTextReportBody);
        editReportSubject = findViewById(R.id.editTextReportSubject);
        emailToAddressText = findViewById(R.id.textView13);



        buttonReportSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                //need function to send email
                sendMail();

                if(mUserItem.getUserAccess()==0) {
                    Intent intent = new Intent(ReportBugActivity.this, EmployActivity.class);
                    intent.putExtra(USER_IDENTITY,currentId);
                    intent.putExtra(USER_NAME,currentName);
                    intent.putExtra(USER_ACCESS,currentAccess);
                    intent.putExtra(USER_EMAIL,currentEmail);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ReportBugActivity.this, CustomerActivity.class);
                    intent.putExtra(USER_IDENTITY,currentId);
                    intent.putExtra(USER_NAME,currentName);
                    intent.putExtra(USER_ACCESS,currentAccess);
                    intent.putExtra(USER_EMAIL,currentEmail);
                    startActivity(intent);
                }
            }
        });
        buttonReportCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                if(mUserItem.getUserAccess()==0) {
                    Intent intent = new Intent(ReportBugActivity.this, EmployActivity.class);
                    intent.putExtra(USER_IDENTITY,currentId);
                    intent.putExtra(USER_NAME,currentName);
                    intent.putExtra(USER_ACCESS,currentAccess);
                    intent.putExtra(USER_EMAIL,currentEmail);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ReportBugActivity.this, CustomerActivity.class);
                    intent.putExtra(USER_IDENTITY,currentId);
                    intent.putExtra(USER_NAME,currentName);
                    intent.putExtra(USER_ACCESS,currentAccess);
                    intent.putExtra(USER_EMAIL,currentEmail);
                    startActivity(intent);
                }
            }
        });
    }

    private void sendMail() {
        String sentTo = emailToAddressText.getText().toString();
        String subject = editReportSubject.getText().toString();
        String body = editReportBody.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, sentTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, " choose an email client"));
    }
}