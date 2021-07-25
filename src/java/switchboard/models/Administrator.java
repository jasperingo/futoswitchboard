
package switchboard.models;


public class Administrator extends User{
    
    public static final String TABLE = "administrator";
    
    private String password;
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}


