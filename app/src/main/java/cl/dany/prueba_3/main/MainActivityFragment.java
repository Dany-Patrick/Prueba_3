package cl.dany.prueba_3.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.dany.prueba_3.R;
import cl.dany.prueba_3.adapters.PlaceAdapter;
import cl.dany.prueba_3.data.CurrentUser;
import cl.dany.prueba_3.data.EmailProcesor;
import cl.dany.prueba_3.data.Nodes;
import cl.dany.prueba_3.models.Place;
import cl.dany.prueba_3.models.PlaceListener;

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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerVW);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new PlaceAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        getActivity().setTitle(new CurrentUser().email());





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
        Log.d("hola",place.getUid());
    }
}
