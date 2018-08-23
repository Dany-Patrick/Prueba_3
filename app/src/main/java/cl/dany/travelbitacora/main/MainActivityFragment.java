package cl.dany.travelbitacora.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import cl.dany.travelbitacora.R;
import cl.dany.travelbitacora.adapters.PlaceAdapter;
import cl.dany.travelbitacora.data.CurrentUser;
import cl.dany.travelbitacora.data.EmailProcesor;
import cl.dany.travelbitacora.data.Nodes;
import cl.dany.travelbitacora.models.Place;
import cl.dany.travelbitacora.models.PlaceListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements PlaceListener {
    public static final String PLACE = "cl.dany.prueba_3.main.KEY.PLACE";
    private PlaceAdapter adapter;
    CurrentUser user = new CurrentUser();
    String emailSanitized = new EmailProcesor().sanitizedEmail(user.email());


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerVW);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlaceAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        getActivity().setTitle(new CurrentUser().email());

        final Timer timer;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {



                progressDialog.cancel();
                timer.cancel();
            }
        }, 1800 ,1800);


    }

    @Override
    public void clicked(Place place) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(PLACE, place);
        startActivity(intent);

    }

    @Override
    public void clicked2(Place place) {
        new Nodes().pending(emailSanitized).child(place.getUid()).removeValue();
        Log.d("hola", place.getUid());
    }
}
