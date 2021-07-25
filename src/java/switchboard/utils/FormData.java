
package switchboard.utils;

import java.util.HashMap;


public class FormData {
    
    private String formError;
    
    private String formSuccess;
    
    private HashMap<String, String[]> data = new HashMap<>();
    
    private HashMap<String, String> errors = new HashMap<>();

    public String getFormError() {
        return formError;
    }

    public void setFormError(String formError) {
        this.formError = formError;
    }

    public String getFormSuccess() {
        return formSuccess;
    }

    public void setFormSuccess(String formSuccess) {
        this.formSuccess = formSuccess;
    }
    
    public HashMap<String, String[]> getData() {
        return data;
    }

    public void setData(HashMap<String, String[]> data) {
        this.data = data;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
    
    public void putError(String key, String error) {
        errors.put(key, error);
    }
    
    public void putData(String key, String value) {
       putData(key, new String[] {value});
    }
    
    public void putData(String key, String[] value) {
        data.put(key, value);
    }
    
    public boolean hasData() {
        return !data.isEmpty();
    }
    
    public boolean hasErrors() {
        return formError != null || !errors.isEmpty();
    }
    
    public boolean hasError(String key) {
        return errors.containsKey(key);
    }
    
    public boolean hasSuccess() {
        return formSuccess != null;
    }
    
    
}




