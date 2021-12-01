package android.app.appchat.model;

public class ChatMsg {
    String timestamp, user, msg;

    public ChatMsg(String timestamp, String user, String msg) {
        this.timestamp = timestamp;
        this.user = user;
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "timestamp='" + timestamp + '\'' +
                ", user='" + user + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ChatMsg() {
    }


}
