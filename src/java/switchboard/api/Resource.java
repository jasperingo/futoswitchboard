
package switchboard.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import switchboard.api.response.Say;
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
        
        Say say = new Say();
        say.setValue("We did not get your extension code. Good bye");
        
        response.setGetDigits(getDigits);
        response.setSay(say);
        
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
        @FormParam("recordingUrl")String recordingUrl
    ) {
        
        SResponse response = new SResponse();
        Say sayError = new Say();
        sayError.setVoice("woman");
        sayError.setPlayBeep(false);
        sayError.setValue("There was an error, please try again.");
        
        if (extCode == null || extCode.isEmpty()) {
            sayError.setValue("Please enter a valid extension code.");
            response.setSay(sayError);
            return response;
        }
        
        Extension extension = ExtensionDB.findByCode(extCode);
        
        if (extension == null) {
            response.setSay(sayError);
            return response;
        } else if (extension.getId() < 1) {
            sayError.setValue("You entered an invalid extension code. Good bye");
            response.setSay(sayError);
            return response;
        }
        
        CallReport report = new CallReport();
        report.setCallerNumber(callerNum);
        report.setStatus(CallReport.STATUS_ACTIVE);
        report.setSessionId(sessionId);
        report.setStaffExtension(extension.getStaffExtensions().get(0));
        //report.setStartedAt(LocalDateTime.parse(callStartTime, DateTimeFormatter.ISO_DATE));
        
        
        if (extension.isSecured()) {
            
            report.setStatus(CallReport.STATUS_PENDING);
             if (!CallReportDB.insert(report)) {
                response.setSay(sayError);
                return response;
            }
             
            Say say = new Say();
            say.setValue("Please enter access pin");

            GetDigits getDigits = new GetDigits();
            getDigits.setSay(say);
            getDigits.setFinishOnKey("#");
            getDigits.setTimeout(20);
            getDigits.setCallbackUrl("http://switchboard.iftlab.com.ng:8080/SB/api/v1/secured");
               
            response.setGetDigits(getDigits);
            return response;
        }
        
        
        if (!CallReportDB.insert(report)) {
            response.setSay(sayError);
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
        @FormParam("recordingUrl")String recordingUrl
    ) {
        
        SResponse response = new SResponse();
        Say sayError = new Say();
        sayError.setVoice("woman");
        sayError.setPlayBeep(false);
        sayError.setValue("There was an error, please try again.");
        
        
        CallReport report = CallReportDB.findBySessionId(sessionId);
        
        if (report == null) {
            response.setSay(sayError);
            return response;
        } else if (report.getId() < 1) {
            sayError.setValue("This session has ended. Good bye");
            response.setSay(sayError);
            return response;
        }
        
        if (report.getStaffExtension().getExtension().getPin().equals(pin)) {
            
            int result = CallReportDB.updateStatus(CallReport.STATUS_ACTIVE, report.getId());
            if (result <= 0) {
                response.setSay(sayError);
                return response;
            }
            
            response.setDial(makeDial(report.getStaffExtension().getStaff().getPhoneNumber()));
            
        } else {
            sayError.setValue("You entered an incorrect pin, your call cannot be connected.");
            response.setSay(sayError);
        }
        
        return response;
    }
    
    
    @POST
    @Path("end")
    public SResponse endCall(
        @FormParam("isActive") String isActive, 
        @FormParam("sessionId") String sessionId,
        @FormParam("amount") String amount,
        @FormParam("callStartTime") String callStartTime,
        @FormParam("durationInSeconds")String duration, 
        @FormParam("recordingUrl")String recordingUrl) {
        
        
        CallReportDB.updateStatusWhenCallEnds(recordingUrl, 
                Integer.parseInt(duration), Double.parseDouble(amount), sessionId);
        
        return null;
        
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




