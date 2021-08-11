package com.example.cut2016.loginsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    EditText userName, userPassword;

    Intent intent;

    String userKey;

    Register userRecord;

    boolean isDataValid;

    HashMap<String, Register> registerList;

    final int REGISTER_CODE = 1, WELCOME_CODE = 2, RESET_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        userKey = "";

        userRecord = null;

        userName = (EditText)findViewById(R.id.loginUserNameTxt);

        userPassword = (EditText)findViewById(R.id.userPasswordTxt);

        registerList = new HashMap<>();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case REGISTER_CODE:

                if (resultCode == RESULT_OK)
                {
                    // Extracting data returned from the child Activity.
                    if(data.getSerializableExtra("registerList") != null)
                    {
                        registerList = (HashMap<String, Register>) data.getSerializableExtra("registerList");

                        if(!registerList.isEmpty())
                        {
                            userKey = data.getStringExtra("userKey");

                            ShowToast(getString(R.string.registration_message, registerList.get(userKey).getFullName()));
                        }
                    }
                }

                ClearViews(userName, userPassword);

                break;

            case WELCOME_CODE:

                if (resultCode == RESULT_OK)
                {
                    // Extracting data returned from the child Activity.
                    if(data.getSerializableExtra("userRecord") != null)
                    {
                        userRecord = (Register) data.getSerializableExtra("userRecord");

                        ShowToast(getString(R.string.logout_message, userRecord.getFullName()));
                    }
                }

                ClearViews(userName, userPassword);

                break;

            case  RESET_CODE:

                if (resultCode == RESULT_OK)
                {
                    // Extracting data returned from the child Activity.
                    if(data.getSerializableExtra("userRecord") != null)
                    {
                        registerList = (HashMap<String, Register>) data.getSerializableExtra("registerList");

                        userKey = data.getStringExtra("userKey");

                        ShowToast(getString(R.string.passwordChange_message, registerList.get(userKey).getFullName()));
                    }
                }

                break;
        }
    }

    public void Button_Click(View view)
    {

        switch (view.getId())
        {
            case R.id.loginBtn:

                isDataValid = true;

                if(userName.getText().toString().isEmpty())
                {
                    isDataValid = false;

                    SetViewError(new String[]{"Please Enter Valid Username"}, userName);
                }

                if(userPassword.getText().toString().isEmpty())
                {
                    isDataValid = false;

                    SetViewError(new String[]{"Please Enter Valid Password"}, userPassword);
                }

                if(!isDataValid)
                {
                    ShowToast("Please Provide Both UserName and Password To Login");
                }
                else
                {
                    if(registerList != null)
                    {
                        if(!registerList.isEmpty())
                        {
                            if (registerList.containsKey(userName.getText().toString()))
                            {
                                Register user = registerList.get(userName.getText().toString());

                                if (user.getUserPassword().equals(userPassword.getText().toString()))
                                {
                                    intent = new Intent(this, WelcomeActivity.class);

                                    intent.putExtra("userRecord", user);

                                    startActivityForResult(intent, WELCOME_CODE);
                                }
                                else
                                {
                                    ClearViews(userName, userPassword);

                                    ShowToast(getString(R.string.loginError_Message));
                                }
                            }
                            else
                            {
                                ClearViews(userName, userPassword);

                                ShowToast(getString(R.string.loginError_Message));
                            }
                        }
                        else
                        {
                            ClearViews(userName, userPassword);

                            ShowToast(getString(R.string.noUserError_Message));
                        }
                    }
                    else
                    {
                        ClearViews(userName, userPassword);

                        ShowToast(getString(R.string.noUserError_Message));
                    }
                }
                break;
            case R.id.registerBtn:

                intent = new Intent(this, RegisterActivity.class);

                intent.putExtra("registerList", registerList);

                startActivityForResult(intent, REGISTER_CODE);

                break;
            case R.id.resetBtn:

                intent = new Intent(this, ResetActivity.class);

                intent.putExtra("registerList", registerList);

                startActivityForResult(intent, RESET_CODE);

                break;
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

    private  void ShowToast(String message)
    {
        Toast.makeText(this, message, LENGTH_LONG).show();
    }
}