package org.bigmouth.framework.web_socket;

import org.bigmouth.framework.util.BaseLifeCycleSupport;

import com.google.common.base.Preconditions;


public class WebSocketFactory extends BaseLifeCycleSupport {

    private final BigmouthWebSocketServer webSocket;
    
    public WebSocketFactory(BigmouthWebSocketServer webSocket) {
        Preconditions.checkNotNull(webSocket, "webSocket cannot be null.");
        this.webSocket = webSocket;
    }

    @Override
    protected void doInit() {
        webSocket.startup();
    }

    @Override
    protected void doDestroy() {
        if (null != webSocket) {
            webSocket.shutdown();
        }
    }
    
    public BigmouthWebSocketServer getWebSocket() {
        return webSocket;
    }
}
