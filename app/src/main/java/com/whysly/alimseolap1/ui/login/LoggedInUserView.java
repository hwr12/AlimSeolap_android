package com.whysly.alimseolap1.ui.login;

import com.google.firebase.auth.FirebaseUser;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }
    LoggedInUserView(FirebaseUser user) {
        this.displayName = user.getDisplayName();
    }


    String getDisplayName() {
        return displayName;
    }
}