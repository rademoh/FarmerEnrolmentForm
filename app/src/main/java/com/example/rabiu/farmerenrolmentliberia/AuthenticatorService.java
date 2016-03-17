package com.example.rabiu.farmerenrolmentliberia;

/**
 * Created by Rabiu on o1/28/15.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * A bound Service that instantiates the authenticator
 * when started.
 */
public class AuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
        Toast.makeText(this, "AuthenticatorService initialized", Toast.LENGTH_SHORT).show();
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"AUTHENTICATORSERVICE onBind", Toast.LENGTH_SHORT).show();
        return mAuthenticator.getIBinder();
    }
}
