package org.bigmouth.framework.web_socket;


public interface BigmouthWebSocketServer {

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     * 
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    void print(String text);
    
    /**
     * Sends <var>data</var> to all currently connected WebSocket clients.
     * 
     * @param data
     *            The byte array to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    void print(byte[] data);
    
    /**
     * Startup the WebSocket Server.
     */
    void startup();
    
    /**
     * Shutdown the WebSocket Server.
     */
    void shutdown();
    
    /**
     * Returns the WebSocket Server connect address.
     * 
     * @return
     */
    String getServerAddr();
}
