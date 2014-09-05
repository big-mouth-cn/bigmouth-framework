package org.bigmouth.framework.web_socket.ws;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.bigmouth.framework.web_socket.BigmouthWebSocketServer;
import org.bigmouth.framework.web_socket.WebSocketFactory;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NativeWebSocket extends WebSocketServer implements BigmouthWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketFactory.class);
    private String accessAddr;
    
    public NativeWebSocket(String host, int port) {
        super(new InetSocketAddress(host, port));
    }
    
    public NativeWebSocket(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void print(String text) {
        Collection<WebSocket> connections = connections();
        for (WebSocket webSocket : connections) {
            webSocket.send(text);
        }
    }

    @Override
    public void print(byte[] data) {
        Collection<WebSocket> connections = connections();
        for (WebSocket webSocket : connections) {
            webSocket.send(data);
        }
    }

    @Override
    public void startup() {
        start();
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("WebSocket Server " + getAddress() + " has startup!");
        }
    }

    @Override
    public void shutdown() {
        try {
            stop();
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("WebSocket Server " + getAddress() + " has shutdown!");
            }
        }
        catch (IOException e) {
            LOGGER.error("shutdown: ", e);
        }
        catch (InterruptedException e) {
            LOGGER.error("shutdown: ", e);
        }
    }

    @Override
    public String getServerAddr() {
        if (StringUtils.isNotBlank(accessAddr))
            return accessAddr;
        InetSocketAddress address = getAddress();
        return new StringBuilder("ws://").append(address.getAddress().getHostAddress())
            .append(":").append(address.getPort()).toString();
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(conn.getRemoteSocketAddress() + " Create a connection!");
        }
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(conn.getRemoteSocketAddress() + " Has lost!");
        }
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(conn.getRemoteSocketAddress() + ", " + message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        print(ex.getMessage());
    }

    public void setAccessAddr(String accessAddr) {
        this.accessAddr = accessAddr;
    }
}
