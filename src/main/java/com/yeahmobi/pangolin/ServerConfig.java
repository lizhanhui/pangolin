package com.yeahmobi.pangolin;

public class ServerConfig {

    private int port = 8080;

    private int connectTimeout = 3000;

    private int sendBufferSize = 1024 * 1024 * 8;

    private int maxReceiveBufferSize = 1024 * 1024 * 16;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    public int getMaxReceiveBufferSize() {
        return maxReceiveBufferSize;
    }

    public void setMaxReceiveBufferSize(int maxReceiveBufferSize) {
        this.maxReceiveBufferSize = maxReceiveBufferSize;
    }
}
