
package switchboard.api.response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "GetDigits")
public class GetDigits extends BaseResponse {
    
    @XmlAttribute
    private String callbackUrl;
    
    @XmlAttribute
    private Integer numDigits;
    
    @XmlAttribute
    private Integer timeout;
    
    @XmlAttribute
    private String finishOnKey;
    
    @XmlElement(name = "Say")
    private Say say;
    
    @XmlElement(name = "Play")
    private Play play;
    

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Integer getNumDigits() {
        return numDigits;
    }

    public void setNumDigits(Integer numDigits) {
        this.numDigits = numDigits;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getFinishOnKey() {
        return finishOnKey;
    }

    public void setFinishOnKey(String finishOnKey) {
        this.finishOnKey = finishOnKey;
    }

    public Say getSay() {
        return say;
    }

    public void setSay(Say say) {
        this.say = say;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
    
    
    
    
}


