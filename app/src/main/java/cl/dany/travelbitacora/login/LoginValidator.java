package cl.dany.travelbitacora.login;

import cl.dany.travelbitacora.data.CurrentUser;

public class LoginValidator {
    private LoginCallback callBack;

    public LoginValidator(LoginCallback callBack) {
        this.callBack = callBack;
    }

    public void checkLogin() {
        if (new CurrentUser().getCurrentUser() != null) {
            callBack.logged();
        } else {
            callBack.signUp();
        }
    }
}
