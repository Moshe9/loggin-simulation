package com.example.cut2016.loginsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class ResetActivity extends AppCompatActivity {

    Register userRecord;

    boolean isDataValid;

    HashMap<String, Register> registerList;

    EditText userName, oldPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reset_activity);

        registerList = new HashMap<>();

        userName = (EditText) findViewById(R.id.reset_UserNameTxt);

        oldPassword = (EditText) findViewById(R.id.reset_OldPasswordTxt);

        newPassword = (EditText) findViewById(R.id.reset_NewPasswordTxt);

        confirmPassword = (EditText) findViewById(R.id.reset_ConfirmPasswordTxt);

        if(getIntent().getSerializableExtra("registerList") != null)
        {
            registerList = (HashMap<String, Register>) getIntent().getSerializableExtra("registerList");
        }
    }

    public void Reset_OnClickEvent(View view)
    {
        isDataValid = true;

        if(userName.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Enter Username"}, userName);
        }

        if(oldPassword.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Enter Old Password"}, oldPassword);
        }

        if(newPassword.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Enter New Password"}, newPassword);
        }

        if(confirmPassword.getText().toString().isEmpty())
        {
            isDataValid = false;

            SetViewError(new String[]{"Please Confirm Password"}, confirmPassword);
        }

        if(!isDataValid)
        {
            ShowToast(getString(R.string.missingDataError_Message));
        }
        else
        {
            if (!newPassword.getText().toString().equals(confirmPassword.getText().toString()))
            {
                ShowToast(getString(R.string.password_Mismatch));
            }
            else
            {
                if(registerList != null)
                {
                    if(!registerList.isEmpty())
                    {
                        if (registerList.containsKey(userName.getText().toString()))
                        {
                            userRecord = registerList.get(userName.getText().toString());

                            if(oldPassword.getText().toString().equals(userRecord.getUserPassword()))
                            {
                                if(!userRecord.getUserPassword().equals(newPassword.getText().toString()))
                                {
                                    userRecord.setUserPassword(newPassword.getText().toString());

                                    registerList.remove(userRecord.getUserName());

                                    registerList.put(userRecord.getUserName(), userRecord);

                                    Intent intent = new Intent();

                                    intent.putExtra("registerList", registerList);

                                    intent.putExtra("userKey", userRecord.getUserName());

                                    setResult(RESULT_OK, intent);

                                    this.finish();
                                }
                                else
                                {
                                    ShowToast(getString(R.string.passwordError_Message));

                                    ClearViews(newPassword, confirmPassword);
                                }
                            }
                            else
                            {
                                ShowToast(getString(R.string.resetError_Message));

                                ClearViews(userName, oldPassword, newPassword, confirmPassword);
                            }
                        }
                        else
                        {
                            ShowToast(getString(R.string.resetError_Message));

                            ClearViews(userName, oldPassword, newPassword, confirmPassword);
                        }
                    }
                    else
                    {
                        ShowToast(getString(R.string.noUserError_Message));

                        ClearViews(userName, oldPassword, newPassword, confirmPassword);
                    }
                }
                else
                {
                    ShowToast(getString(R.string.noUserError_Message));

                    ClearViews(userName, oldPassword, newPassword, confirmPassword);
                }
            }
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