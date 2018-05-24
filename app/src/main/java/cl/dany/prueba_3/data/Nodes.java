package cl.dany.prueba_3.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nodes {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference users()
    {
        return root.child("users");
    }
    public DatabaseReference user(String key)
    {
        return users().child(key);
    }
    public DatabaseReference pending()
    {
        return root.child("pendings");
    }
    public DatabaseReference userChat(String uid)
    {
        return pending().child(uid);
    }
    public DatabaseReference messages(String chatId){return root.child("messages").child(chatId);}
}
