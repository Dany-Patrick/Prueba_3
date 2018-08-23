package cl.dany.travelbitacora.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import cl.dany.travelbitacora.R;
import cl.dany.travelbitacora.data.CurrentUser;
import cl.dany.travelbitacora.data.EmailProcesor;
import cl.dany.travelbitacora.data.Nodes;
import cl.dany.travelbitacora.models.Place;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    public static final String PLACE = "cl.dany.prueba_3.main.KEY.PLACE";
    private Place place;
    CurrentUser user = new CurrentUser();
    String emailSanitized = new EmailProcesor().sanitizedEmail(user.email());
    private TextView textViewRating;
    private EditText editTextDescription;
    private RatingBar ratingBar;
    private float ranking;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        place = (Place) getActivity().getIntent().getSerializableExtra(MainActivityFragment.PLACE);
        getActivity().setTitle(place.getName());

        ratingBar = view.findViewById(R.id.starDetail);
        textViewRating = view.findViewById(R.id.itemTextRanking);
        editTextDescription = view.findViewById(R.id.placeDetailTv);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ranking = rating;

                if (ranking == 0.0 || ranking == 0.5) {

                    textViewRating.setText("Horrible");
                } else if (ranking == 1.0 || ranking == 1.5) {
                    textViewRating.setText("Malisimo");
                } else if (ranking == 2.0 || ranking == 2.5) {
                    textViewRating.setText("Más o menos");
                } else if (ranking == 3.0 || ranking == 3.5) {
                    textViewRating.setText("Bueno");
                } else if (ranking == 4.0 || ranking == 4.5) {
                    textViewRating.setText("Muy Bueno");
                } else if (ranking == 5.0) {
                    textViewRating.setText("Excelente!!!");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        place.setDescription(editTextDescription.getText().toString());
        place.setRanking(ranking);
        if (place.getDescription() != null || place.getRanking() != 0) {
            place.setVisited(true);
        }

        new Nodes().pending(emailSanitized).child(place.getUid()).child("description").setValue(place.getDescription());
        new Nodes().pending(emailSanitized).child(place.getUid()).child("ranking").setValue(place.getRanking());
        new Nodes().pending(emailSanitized).child(place.getUid()).child("visited").setValue(place.isVisited());
        new Nodes().pending(emailSanitized).child(place.getUid()).child("latitude").setValue(place.getLatitude());
        new Nodes().pending(emailSanitized).child(place.getUid()).child("longitude").setValue(place.getLongitude());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (place.getDescription() != null) {
            float ranking = place.getRanking();
            editTextDescription.setText(place.getDescription());
            ratingBar.setRating(ranking);


        }
    }

}
