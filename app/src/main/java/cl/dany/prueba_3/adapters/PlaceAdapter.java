package cl.dany.prueba_3.adapters;


public class PlaceAdapter{
    /* extends FirebaseRecyclerAdapter<Place, PlaceAdapter.PlaceHolder> {



    public PlaceAdapter(LifecycleOwner lifecycleOwner) {
        super(new FirebaseRecyclerOptions.Builder<Place>()
                .setQuery(new Nodes().userChat(new CurrentUser().uid()),Chat.class)
                .setLifecycleOwner(lifecycleOwner)
                .build()
        );

    }



    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place,parent, false);
        return new PlaceHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlaceHolder holder, int position, @NonNull Place model) {
        if(model.isNotification())
        {
            holder.view.setVisibility(View.VISIBLE);
        }else
        {
            holder.view.setVisibility(View.GONE);
        }

        holder.textView.setText(model.getReceiver());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat auxChat = getItem(holder.getAdapterPosition());
                listener.clicked(auxChat);
            }
        });
    }


    public class PlaceHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private View view;
        public PlaceHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.emailTv);
            view = itemView.findViewById(R.id.notificationV);

        }
    }*/

}
