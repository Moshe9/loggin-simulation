package com.example.cut2016.loginsimulation;

import java.io.Serializable;

/**
 * Created by cut on 2/10/2017.
 */

class Register  implements Serializable {

    private String userName;
    private String fullName;
    private String userPassword;

    Register(String userName, String fullName, String userPassword)
    {
        this.userName = userName;

        this.fullName = fullName;

        this.userPassword = userPassword;
    }

    String getUserName()
    {
        return userName;
    }

    void  setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    String getUserPassword() {
        return userPassword;
    }

    String getFullName() {
        return fullName;
    }
}