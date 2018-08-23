package cl.dany.travelbitacora.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.dany.travelbitacora.R;
import cl.dany.travelbitacora.data.CurrentUser;
import cl.dany.travelbitacora.data.EmailProcesor;
import cl.dany.travelbitacora.data.Nodes;
import cl.dany.travelbitacora.login.LoginActivity;
import cl.dany.travelbitacora.models.Place;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    private Place place = new Place();
    private CurrentUser user = new CurrentUser();
    private String emailSanitized = new EmailProcesor().sanitizedEmail(user.email());
    private static final int RC_GEOLOCATION = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, RC_GEOLOCATION);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //se llama al dialog y se le pasa el contexto del main principal
                final Dialog dialog = new Dialog(MainActivity.this);
                //se indica al dialog que no tenga titulo
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //se le pasa al dialogo el layout
                dialog.setContentView(R.layout.dialog_place);

                ImageButton imageButton = dialog.findViewById(R.id.savePlaceBtn);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //se llama el textview del dialog


                        EditText editText = dialog.findViewById(R.id.placeListItem);
                        String name = editText.getText().toString();


                        String key = new Nodes().pending(emailSanitized).push().getKey();
                        place = new Place(name, "", key, emailSanitized, " ","",0,false);

                        if (name.trim().length() > 0) {
                            //se guarda un nuevo Place con el Name
                            new Nodes().pending(emailSanitized).child(key).setValue(place);

                        }
                        //se cierra el dialog
                        dialog.dismiss();
                    }
                });
                //se le indica al dialog que tenga ancho completo
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                //se muestra el dialog
                dialog.show();
            }
        });

        TextView textView = findViewById(R.id.logoutTv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGGIN", "OnCLIK");
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("LOGGIN", "OnComplete");
                                // user is now signed out
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                MainActivity.this.finish();

                            }
                        });

            }
        });

        TextView textView2 = findViewById(R.id.activityTV);
        textView2.setText(new CurrentUser().email());

        // TODO: Move this to where you establish a user session
        logUser();

    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }
    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("polnareff");
        Crashlytics.setUserEmail("davergarap@uc.cl");
        Crashlytics.setUserName("Dany-Patrick");
    }

}
