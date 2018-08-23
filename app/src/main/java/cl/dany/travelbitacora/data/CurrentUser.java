package cl.dany.travelbitacora.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String uid() {
        return currentUser.getUid();
    }

    public String email() {
        return currentUser.getEmail();
    }
}
