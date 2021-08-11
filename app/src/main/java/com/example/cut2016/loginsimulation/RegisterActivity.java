package com.example.cut2016.loginsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, fullName, password, confirmPassword;

    HashMap<String, Register> registerList;

    boolean isDataValid, isUniqueUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_activity);

        userName = (EditText)findViewById(R.id.userNameTxt);

        fullName = (EditText)findViewById(R.id.fullNameTxt);

        password = (EditText)findViewById(R.id.passwordTxt);

        confirmPassword = (EditText)findViewById(R.id.confirmPasswordTxt);

        registerList = new HashMap<>();

        if (getIntent().getSerializableExtra("registerList")!= null)
        {
            registerList = (HashMap<String, Register>) getIntent().getSerializableExtra("registerList");
        }
    }

    public void Register_Click(View view) {

        isDataValid = true;

        isUniqueUser = true;

        Register newUser;

        if(userName.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Provide Username"}, userName);
        }

        if(fullName.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Provide Full Name"}, fullName);
        }

        if(password.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Provide Password"}, password);
        }

        if(confirmPassword.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Provide Password Confirmation"}, confirmPassword);
        }

        if (isDataValid)
        {
            if (!password.getText().toString().equals(confirmPassword.getText().toString()))
            {
                SetViewError(new String[]{"Password Mismatch Detected", "Password Mismatch Detected"}, password,
                        confirmPassword);

                ClearViews(password, confirmPassword);

                ShowToast(getString(R.string.password_Mismatch));
            }
            else
            {
                if (!registerList.isEmpty())
                {
                    if(registerList.containsKey(userName.getText().toString()))
                    {
                        isUniqueUser = false;
                    }
                }

                if(!isUniqueUser)
                {
                    ClearViews(userName);

                    userName.setHint("User Name Already Taken....");

                    ShowToast(getString(R.string.duplicateError_Message));
                }
                else
                {
                    newUser = new Register(userName.getText().toString(),
                            fullName.getText().toString(),
                            password.getText().toString());

                    registerList.put(userName.getText().toString(), newUser);

                    Intent intent = new Intent();

                    intent.putExtra("registerList", registerList);

                    intent.putExtra("userKey", newUser.getUserName());

                    setResult(RESULT_OK, intent);

                    this.finish();
                }

            }
        }
        else
        {
            ShowToast(getString(R.string.missingDataError_Message));
        }
    }

    private void ClearViews(EditText... views)
    {
        for (EditText item : views)
        {
            item.getText().clear();
        }
    }

    private void SetViewError(String[] errorMessage, EditText... views)
    {
        int index = 0;

        for (EditText item : views)
        {
            item.setError(errorMessage[index]);

            ++index;
        }
    }

    private void ShowToast(String message)
    {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }
}