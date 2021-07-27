
package switchboard.api.response;

import javax.xml.bind.annotation.XmlAttribute;


public class Dial extends BaseResponse {
    
    @XmlAttribute
    private String phoneNumbers;
    
    @XmlAttribute
    private boolean record;
    
    @XmlAttribute
    private boolean sequential;
    
    @XmlAttribute
    private String callerId;
    
    @XmlAttribute
    private String ringBackTone;
    
    @XmlAttribute
    private long maxDuration;
    
    
    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getRingBackTone() {
        return ringBackTone;
    }

    public void setRingBackTone(String ringBackTone) {
        this.ringBackTone = ringBackTone;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }
    
    
    
    
}


