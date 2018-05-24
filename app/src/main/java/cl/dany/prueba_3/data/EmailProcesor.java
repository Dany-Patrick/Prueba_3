package cl.dany.prueba_3.data;

public class EmailProcesor {
    public String sanitizedEmail(String email)
    {
        return email.replace("@","AT").replace(".","DOT");
    }

}
