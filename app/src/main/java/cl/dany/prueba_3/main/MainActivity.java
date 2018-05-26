package cl.dany.prueba_3.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.dany.prueba_3.R;
import cl.dany.prueba_3.data.CurrentUser;
import cl.dany.prueba_3.data.EmailProcesor;
import cl.dany.prueba_3.data.Nodes;
import cl.dany.prueba_3.login.LoginActivity;
import cl.dany.prueba_3.models.Place;

public class MainActivity extends AppCompatActivity {
Place place = new Place();
    CurrentUser user = new CurrentUser();
    String emailSanitized = new EmailProcesor().sanitizedEmail(user.email());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                        place = new Place(name, "", key, emailSanitized, 0, false);

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
                Log.d("LOGGIN","OnCLIK");
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("LOGGIN","OnComplete");
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


    }


}
