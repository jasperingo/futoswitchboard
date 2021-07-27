
package switchboard.models;

import java.time.LocalDateTime;


public class CallReport extends Entity {
    
    public static final String TABLE = "call_report";
    
    public static final String STATUS_PENDING = "pending";
    
    public static final String STATUS_ACTIVE = "active";
    
    public static final String STATUS_ENDED = "ended";
    
    
    private String sessionId;
    
    private String callerNumber;
    
    private StaffExtension staffExtension;
    
    private String recordUrl;
    
    private String status;
    
    private LocalDateTime startedAt;
    
    private int duration;
    
    private double cost;
    
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    public StaffExtension getStaffExtension() {
        return staffExtension;
    }

    public void setStaffExtension(StaffExtension staffExtension) {
        this.staffExtension = staffExtension;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
    
    
}


