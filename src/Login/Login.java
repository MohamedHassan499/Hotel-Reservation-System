package Login;

/**
 *
 * @author Mohamed
 */
interface Login {

    String getUserName();

    String getPassword();
}

class Receptionist implements Login {

    String u, p;

    public Receptionist(String user, String pass) {
        u = user;
        p = pass;
    }

    public String getUserName() {
        return u;
    }

    public String getPassword() {
        return p;
    }
}
