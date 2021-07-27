
package switchboard.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import switchboard.api.response.Dial;
import switchboard.api.response.GetDigits;
import switchboard.api.response.Play;
import switchboard.api.response.Reject;
import switchboard.api.response.SResponse;
import switchboard.database.CallReportDB;
import switchboard.database.ExtensionDB;
import switchboard.models.CallReport;
import switchboard.models.Extension;


@Path("v1")
@Produces(MediaType.APPLICATION_XML)
public class Resource {
    
    public Resource() {
    }

    
    @POST
    @Path("start")
    public SResponse home(@FormParam("isActive") String isActive) {
        
        SResponse response = new SResponse();
        
        if (!isActive.equals("1")) {
            Reject rej = new Reject();
            response.setReject(rej);
            return response;
        }
        
        Play play = new Play();
        play.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1624018988/SWITCHBOARD_bgd1dy.mp3");
        
        GetDigits getDigits = new GetDigits();
        getDigits.setPlay(play);
        getDigits.setFinishOnKey("#");
        getDigits.setTimeout(20);
        getDigits.setCallbackUrl("http://switchboard.iftlab.com.ng:8080/SB/api/v1/route");
        
        Play splay = new Play();
        splay.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402955/Switchboard/3_bs0ccm.mp3");
        
        response.setGetDigits(getDigits);
        response.setPlay(splay);
        
        return response;
    }
    
    
    @POST
    @Path("route")
    public SResponse router(
        @FormParam("isActive") String isActive, 
        @FormParam("sessionId") String sessionId,
        @FormParam("callerNumber") String callerNum, 
        @FormParam("callStartTime") String callStartTime,
        @FormParam("dtmfDigits") String extCode, 
        @FormParam("recordingUrl")String recordingUrl,
        @FormParam("amount") String amount,
        @FormParam("durationInSeconds")String duration
    ) {
        
        if (!isActive.equals("1")) {
            endCall(sessionId, amount, callStartTime, duration, recordingUrl);
            return null;
        }
        
        SResponse response = new SResponse();
        Play playError = new Play();
        playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402954/Switchboard/2_yll63k.mp3");
        
        if (extCode == null || extCode.isEmpty()) {
            playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402957/Switchboard/4_sgbye5.mp3");
            response.setPlay(playError);
            return response;
        }
        
        Extension extension = ExtensionDB.findByCode(extCode);
        
        if (extension == null) {
            response.setPlay(playError);
            return response;
        } else if (extension.getId() < 1) {
            playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402958/Switchboard/5_atahih.mp3");
            response.setPlay(playError);
            return response;
        }
        
        CallReport report = new CallReport();
        report.setCallerNumber(callerNum);
        report.setStatus(CallReport.STATUS_ACTIVE);
        report.setSessionId(sessionId);
        report.setStaffExtension(extension.getStaffExtensions().get(0));
        report.setStartedAt(callStartTime);
        
        
        if (extension.isSecured()) {
            
            report.setStatus(CallReport.STATUS_PENDING);
             if (!CallReportDB.insert(report)) {
                response.setPlay(playError);
                return response;
            }
             
            Play play = new Play();
            play.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402957/Switchboard/6_wy80io.mp3");

            GetDigits getDigits = new GetDigits();
            getDigits.setPlay(play);
            getDigits.setFinishOnKey("#");
            getDigits.setTimeout(20);
            getDigits.setCallbackUrl("http://switchboard.iftlab.com.ng:8080/SB/api/v1/secured");
               
            response.setGetDigits(getDigits);
            return response;
        }
        
        
        if (!CallReportDB.insert(report)) {
            response.setPlay(playError);
            return response;
        }
        
        String thePhone = extension.getStaffExtensions().get(0).getStaff().getPhoneNumber();
        
        response.setDial(makeDial(thePhone));
        
        return response;
    }
    
    
    @POST
    @Path("secured")
    public SResponse secureEnd(
        @FormParam("isActive") String isActive, 
        @FormParam("sessionId") String sessionId,
        @FormParam("dtmfDigits") String pin, 
        @FormParam("recordingUrl")String recordingUrl,
        @FormParam("amount") String amount,
        @FormParam("callStartTime") String callStartTime,
        @FormParam("durationInSeconds")String duration
    ) {
        
        if (!isActive.equals("1")) {
            endCall(sessionId, amount, callStartTime, duration, recordingUrl);
            return null;
        }
        
        SResponse response = new SResponse();
        Play playError = new Play();
        playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402954/Switchboard/2_yll63k.mp3");
        
        CallReport report = CallReportDB.findBySessionId(sessionId);
        
        if (report == null) {
            response.setPlay(playError);
            return response;
        } else if (report.getId() < 1) {
            playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402958/Switchboard/1_upoe9h.mp3");
            response.setPlay(playError);
            return response;
        }
        
        if (report.getStaffExtension().getExtension().getPin().equals(pin)) {
            
            int result = CallReportDB.updateStatus(CallReport.STATUS_ACTIVE, report.getId());
            if (result <= 0) {
                response.setPlay(playError);
                return response;
            }
            
            response.setDial(makeDial(report.getStaffExtension().getStaff().getPhoneNumber()));
            
        } else {
            playError.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1627402959/Switchboard/7_yjg4tk.mp3");
            response.setPlay(playError);
        }
        
        return response;
    }
    
    
    public void endCall(
        String sessionId,
        String amount,
        String callStartTime,
        String duration, 
        String recordingUrl
    ) {
        CallReportDB.updateStatusWhenCallEnds(recordingUrl, 
                Integer.parseInt(duration), Double.parseDouble(amount), sessionId);
    }
    
    
    private Dial makeDial(String thePhone) {
        Dial dial = new Dial();
        dial.setPhoneNumbers("+234"+thePhone.substring(1));
        dial.setRingBackTone("http://sthannah.com.ng/clearday.mp3");
        dial.setRecord(true);
        dial.setMaxDuration(60*60*10);
        dial.setSequential(true);
        return dial;
    }
    
}




