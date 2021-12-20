package br.com.alura.school.exception;

public class MessagesError {
    private String msgUser;
    private String msgDev;

    public MessagesError() {}

    public MessagesError(String msgUser, String msgDev) {
        this.msgUser = msgUser;
        this.msgDev = msgDev;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }

    public String getMsgDev() {
        return msgDev;
    }

    public void setMsgDev(String msgDev) {
        this.msgDev = msgDev;
    }
}
