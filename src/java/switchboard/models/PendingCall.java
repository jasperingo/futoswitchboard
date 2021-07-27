
package switchboard.models;


public class PendingCall extends Entity {
    
    
    private Extension extension;
    
    private String sessionId;

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
    
    
}



