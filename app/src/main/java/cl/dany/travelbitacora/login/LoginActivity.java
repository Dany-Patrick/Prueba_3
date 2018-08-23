package cl.dany.travelbitacora.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import cl.dany.travelbitacora.R;
import cl.dany.travelbitacora.data.CurrentUser;
import cl.dany.travelbitacora.data.EmailProcesor;
import cl.dany.travelbitacora.data.Nodes;
import cl.dany.travelbitacora.main.MainActivity;
import cl.dany.travelbitacora.models.LocalUser;
import io.fabric.sdk.android.Fabric;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());


        setContentView(R.layout.activity_login);
        if (new CurrentUser().getCurrentUser() != null) {
            logged();
        } else {
            signUp();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN == requestCode) {
            if (RESULT_OK == resultCode) {
                logged();
            }
        }
    }

    @Override
    public void signUp() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()))/*
                                new AuthUI.IdpConfig.FacebookBuilder().build()
                                new AuthUI.IdpConfig.TwitterBuilder().build()))*/
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.icono)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void logged() {
        LocalUser user = new LocalUser();

        CurrentUser currentUser = new CurrentUser();
        if (currentUser.getCurrentUser() == null) {
            String key = new EmailProcesor().sanitizedEmail(currentUser.email());
            user.setEmail(currentUser.email());
            user.setName(currentUser.getCurrentUser().getDisplayName());
            user.setUid(currentUser.uid());
            new Nodes().user(key).setValue(user);
            Log.d("Logged", "entré al IF");
        }


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }

}
