
package switchboard.models;


public class StaffExtension extends Entity {
    
    public static final String TABLE = "staff_extension";
    
    private Staff staff;
    
    private Extension extension;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
    
    
    
}



