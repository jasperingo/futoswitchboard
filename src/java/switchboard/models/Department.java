
package switchboard.models;


public class Department extends Entity {
    
    public static final String TABLE = "department";
    
    private String name;
    
    private String prefix;
    
    private boolean minor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }
    
    
    
}


