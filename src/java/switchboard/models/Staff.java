
package switchboard.models;


public class Staff extends User {
    
    public static final String TABLE = "staff";
    
    public static final String GENDER_MALE = "male";
    
    public static final String GENDER_FEMALE = "female";
    
    private String gender;
    
    private String designation;
    
    private StaffExtension staffExtension;
    

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public StaffExtension getStaffExtension() {
        return staffExtension;
    }

    public void setStaffExtension(StaffExtension staffExtension) {
        this.staffExtension = staffExtension;
    }
    
    
    
    
}


