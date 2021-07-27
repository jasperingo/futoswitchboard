
package switchboard.api.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Response")
public class SResponse extends BaseResponse {
    
    @XmlElement(name = "GetDigits")
    private GetDigits getDigits;
    
    private Reject reject;
    
    private Redirect redirect;
    
    @XmlElement(name = "Say")
    private Say say;
    
    @XmlElement(name = "Play")
    private Play play;
    
    private Dial dial;
    
    private Record record;
    
    private Enqueue enqueue;
    
    private Dequeue dequeue;
    
    
    public Reject getReject() {
        return reject;
    }

    public void setReject(Reject reject) {
        this.reject = reject;
    }

    public Redirect getRedirect() {
        return redirect;
    }

    public void setRedirect(Redirect redirect) {
        this.redirect = redirect;
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

    public GetDigits getGetDigits() {
        return getDigits;
    }

    public void setGetDigits(GetDigits getDigits) {
        this.getDigits = getDigits;
    }

    public Dial getDial() {
        return dial;
    }

    public void setDial(Dial dial) {
        this.dial = dial;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Enqueue getEnqueue() {
        return enqueue;
    }

    public void setEnqueue(Enqueue enqueue) {
        this.enqueue = enqueue;
    }

    public Dequeue getDequeue() {
        return dequeue;
    }

    public void setDequeue(Dequeue dequeue) {
        this.dequeue = dequeue;
    }
    
    
    
    
}


