
package switchboard.models;

import java.util.List;


public class Extension extends Entity {
    
    public static final String TABLE = "extension";
    
    private Department department;
    
    private String code;
    
    private boolean secured;
    
    private String pin;
    
    private List<StaffExtension> staffExtensions; 
    
    
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public List<StaffExtension> getStaffExtensions() {
        return staffExtensions;
    }

    public void setStaffExtensions(List<StaffExtension> staffExtensions) {
        this.staffExtensions = staffExtensions;
    }
    
    
    
    
    
}


