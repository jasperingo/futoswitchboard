
package switchboard.api.response;


import javax.xml.bind.annotation.XmlAttribute;;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Say")
public class Say extends BaseResponse {
    
    @XmlAttribute
    private String voice;
    
    @XmlAttribute
    private boolean playBeep;
    
    
    public String value;
    
    
    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public boolean isPlayBeep() {
        return playBeep;
    }

    public void setPlayBeep(boolean playBeep) {
        this.playBeep = playBeep;
    }
    
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}


