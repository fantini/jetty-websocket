package br.gov.pr.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import br.gov.pr.server.endpoint.PrintEndPoint;

public class WebSocketServer {
	
	public static int DEFAULT_SERVER_PORT = 8480;
	public static String SERVER_PORT_OPTION = "server.port";
	
	public static void main(String[] args)
    {
		Server server = new Server();
        
		ServerConnector connector = new ServerConnector(server);
        
		connector.setPort(getServerPort());
        
        server.addConnector(connector);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        
        context.setContextPath("/");
        
        server.setHandler(context);

        try
        {
            
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
            
            wscontainer.addEndpoint(PrintEndPoint.class);

            server.start();
            
            server.dump(System.err);
            
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
	
	public static int getServerPort() {
		return System.getProperty(SERVER_PORT_OPTION) != null ? Integer.valueOf(System.getProperty(SERVER_PORT_OPTION)) : DEFAULT_SERVER_PORT;
	}
}
