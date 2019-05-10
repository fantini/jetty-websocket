package br.gov.pr.server.endpoint;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.pdfbox.pdmodel.PDDocument;

import br.gov.pr.server.print.PrintUtils;
import br.gov.pr.server.print.paper.PaperSize;

@ClientEndpoint
@ServerEndpoint(value="/print")
public class PrintEndPoint {

	private final static Logger LOGGER = Logger.getLogger(PrintEndPoint.class.getName());
	
	@OnOpen
    public void onWebSocketConnect(Session session)
    {
		
		session.setMaxIdleTimeout(30*60*1000);
		session.setMaxTextMessageBufferSize(5*1024*1024);
		
		LOGGER.info("Socket connected on " + session.getRequestURI());
    }
    
    @OnMessage
    public void onWebSocketText(String message)
    {
    	try {
    		
    		LOGGER.info("Initializing print service ...");
    		
			try (PDDocument document = PDDocument.load(Base64.getDecoder().decode(message))) {
	        
		        LOGGER.info("Initiating sending the file to the print server");
		        		        
		        PrintUtils.print(document, PaperSize.A4);
		        
			}
	        
	        LOGGER.info("Finished sending the file to the print server");
			
    	} catch(Exception e) {
    		LOGGER.log(Level.SEVERE, e.getMessage(), e.getCause());
    	}
    }    
    
    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
    	LOGGER.info("Socket closed: " + reason);
    }
    
    @OnError
    public void onWebSocketError(Throwable cause)
    {
    	LOGGER.log(Level.SEVERE, cause.getMessage(), cause);
    }
	
}
