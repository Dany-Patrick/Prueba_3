package cl.dany.prueba_3.login;

import cl.dany.prueba_3.data.CurrentUser;

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
