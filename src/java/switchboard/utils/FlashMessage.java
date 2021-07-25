
package switchboard.utils;


import javax.servlet.http.HttpSession;


public class FlashMessage {
    
    
    private FormData formData;
    
    public FlashMessage(HttpSession session) {
        this(session, "form_data");
    }
    
    public FlashMessage(HttpSession session, String key) {
        formData = (FormData) session.getAttribute(key);
        session.removeAttribute(key);
    }
    
    public boolean hasError() {
        return formData != null && formData.hasErrors();
    }
    
    public boolean hasSuccess() {
        return formData != null && formData.hasSuccess();
    }
    
    public boolean hasData() {
        return formData != null && formData.hasData();
    }
     
    public String getFormError() {
        return formData != null ? (formData.getFormError() == null ? "" : formData.getFormError()) : "";
    }
    
    public String getFormSuccess() {
        return formData != null ? formData.getFormSuccess() : "";
    }
    
    public String getError(String key) {
        return formData != null ? (formData.getErrors().get(key) == null ? "" : formData.getErrors().get(key)) : "";
    }
    
    public String getData(String key) {
        String[] strings = formData != null ? formData.getData().getOrDefault(key, null) : null;
        return strings == null ? "" : strings[0];
    }
    
    public String[] getDataArray(String key) {
        return formData != null ? formData.getData().getOrDefault(key, null) : null;
    }
    
}





















