module server {
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.servlet;
    requires javax.servlet.api;
    requires jersey.container.servlet.core;
    requires java.ws.rs;
    requires java.sql;
    requires javax.websocket.api;
    requires org.eclipse.jetty.websocket.javax.websocket.server;
    requires gson;
    requires org.eclipse.jetty.websocket.javax.websocket;
    requires java.datatransfer;
    requires java.desktop;
    requires messages;
    requires dto;

    opens server;
    exports server;


}
