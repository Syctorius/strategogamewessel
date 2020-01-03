package server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class Service implements Runnable {

    private Server server;
    private static ServerContainer serverContainer;


    public Service(){

    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Service());
        thread.start();
    }

    @Override
    public void run() {
        try{
            server = new Server(2222);

            ServletContextHandler websocketHandler = setWebsocketContext();
            ServletContextHandler restHandler = setRestContext();


            ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
            contextHandlerCollection.setHandlers(new Handler[]{
                    websocketHandler,
                    restHandler
            });

            server.setHandler(contextHandlerCollection);

            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ServletContextHandler setWebsocketContext(){
        ServletContextHandler websocketHandler = null;

        try {
            websocketHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            websocketHandler.setContextPath("/");
            serverContainer = WebSocketServerContainerInitializer.configureContext(websocketHandler);
            addEndPoint();
            //

        } catch (Exception e) {
            e.printStackTrace();
        }

        return websocketHandler;
    }

    public ServletContextHandler setRestContext(){
        ServletContextHandler restHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        restHandler.setContextPath("/rest");
        ServletHolder servletHolder = restHandler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",  LoginService.class.getCanonicalName());

        return restHandler;
    }
    public static void addEndPoint  (){
        try {
            serverContainer.addEndpoint(ServerEndPoint.class);
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }


}