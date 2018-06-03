package cl.dany.prueba_3.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cl.dany.prueba_3.R;

public class DetailsActivity extends AppCompatActivity {
    public static final String PLACE = "cl.dany.prueba_3.main.KEY.PLACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}
