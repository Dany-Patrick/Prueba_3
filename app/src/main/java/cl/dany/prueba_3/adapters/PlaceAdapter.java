package cl.dany.prueba_3.adapters;


import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.dany.prueba_3.R;
import cl.dany.prueba_3.data.CurrentUser;
import cl.dany.prueba_3.data.EmailProcesor;
import cl.dany.prueba_3.data.Nodes;
import cl.dany.prueba_3.models.Place;
import cl.dany.prueba_3.models.PlaceListener;

public class PlaceAdapter extends FirebaseRecyclerAdapter<Place, PlaceAdapter.PlaceHolder> {
    private PlaceListener listener;

    public PlaceAdapter(LifecycleOwner lifecycleOwner, PlaceListener listener) {
        super(new FirebaseRecyclerOptions.Builder<Place>()
                .setQuery(new Nodes().pending(new EmailProcesor().sanitizedEmail(new CurrentUser().email())), Place.class)
                .setLifecycleOwner(lifecycleOwner)
                .build()
        );
        this.listener = listener;


    }


    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place, parent, false);
        return new PlaceHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlaceHolder holder, int position, @NonNull Place model) {
        if (model.getRanking() != 0) {
            holder.itemRatedTV.setVisibility(View.VISIBLE);
            holder.itemRatedTV.setText(String.valueOf(model.getRanking()));
            holder.starIV.setVisibility(View.VISIBLE);
        } else {
            holder.itemRatedTV.setVisibility(View.GONE);
            holder.starIV.setVisibility(View.GONE);
            holder.itemRatedTV.setText(null);
        }

        holder.placeNameTv.setText(model.getName());
        holder.placeNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place auxPlace = getItem(holder.getAdapterPosition());
                listener.clicked(auxPlace);
            }
        });

        holder.placeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place auxPlace = getItem(holder.getAdapterPosition());
                listener.clicked2(auxPlace);
            }
        });

    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView placeNameTv,itemRatedTV;
        private ImageView starIV;
        private ImageView placeBTN;


        public PlaceHolder(View itemView) {
            super(itemView);

            placeNameTv = itemView.findViewById(R.id.placeTextView);
            itemRatedTV = itemView.findViewById(R.id.itemRated);
            starIV = itemView.findViewById(R.id.starIV);
            placeBTN = itemView.findViewById(R.id.placeImgBtn);

        }
    }

}
