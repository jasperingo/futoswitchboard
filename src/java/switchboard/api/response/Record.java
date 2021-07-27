
package switchboard.api.response;


public class Record extends BaseResponse {
    
    private String finishOnKey;
    
    private int maxLength;
    
    private int timeout;
    
    private boolean trimSilence;
    
    private boolean playBeep;
    
    private String callbackUrl;
    
    private Say say;
    
    private Play play;
    
    
    public String getFinishOnKey() {
        return finishOnKey;
    }

    public void setFinishOnKey(String finishOnKey) {
        this.finishOnKey = finishOnKey;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isTrimSilence() {
        return trimSilence;
    }

    public void setTrimSilence(boolean trimSilence) {
        this.trimSilence = trimSilence;
    }

    public boolean isPlayBeep() {
        return playBeep;
    }

    public void setPlayBeep(boolean playBeep) {
        this.playBeep = playBeep;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
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



