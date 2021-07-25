
package switchboard.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import switchboard.api.response.GetDigits;
import switchboard.api.response.Play;
import switchboard.api.response.SResponse;


@Path("v1")
@Produces(MediaType.APPLICATION_XML)
public class Resource {
    
    public Resource() {
    }

   
    @POST
    @Path("start")
    public SResponse home(@FormParam("isActive") String isActive) {
        
        Play play = new Play();
        play.setUrl("https://res.cloudinary.com/dvsscfior/video/upload/v1624018988/SWITCHBOARD_bgd1dy.mp3");
        
        GetDigits getDigits = new GetDigits();
        getDigits.setPlay(play);
        getDigits.setFinishOnKey("#");
        getDigits.setTimeout(20);
        getDigits.setCallbackUrl("http://switchboard.iftlab.com.ng:8080/SB/api/v1/route");
        
        SResponse response = new SResponse();
        response.setGetDigits(getDigits);
        
        
        return response;
    }
    
    
    @POST
    @Path("route")
    public static SResponse router(
        @FormParam("isActive") String isActive, 
        @FormParam("sessionId") String sessionId,
        @FormParam("callerNumber") String callerNum, 
        @FormParam("callStartTime") String callStartTime,
        @FormParam("destinationNumber") String dNumber, 
        @FormParam("dequeuedToPhone") String dequeuedToPhone,
        @FormParam("direction")String direction, 
        @FormParam ("amount") String amount,
        @FormParam("durationinSeconds")String dISecs, 
        @FormParam("dequeueTime")String dTime,
        @FormParam("callerCountryCode")String cCCode, 
        @FormParam("callerCarrierName")String cCN,
        @FormParam("dtmfDigits") String extCode, 
        @FormParam("recordingUrl")String recordingUrl
    ) {
        
        
        
        
        return null;
    }
    
    
    
}




