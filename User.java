
public class User {

    private String userName = null;
    private RSAKey userPublicKey;
    private RSAKey userPrivateKey;
    private String receiverName = null;
    private RSAKey receiverPublicKey;

    public User() { }
    

    public void setUserName(String userName) { this.userName = userName; }

    public void setUserPublicKey(RSAKey userPublicKey) { this.userPublicKey = userPublicKey; }

    public void setUserPrivateKey(RSAKey userPrivateKey) { this.userPrivateKey = userPrivateKey; }

    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public void setReceiverPublicKey(RSAKey receiverPublicKey) { this.receiverPublicKey = receiverPublicKey; }


    public String getUserName() { return userName; }

    public String getReceiverName() { return receiverName; }

    public RSAKey getUserPublicKey() { return userPublicKey; }

    public RSAKey getUserPrivateKey() { return userPrivateKey; }

    public RSAKey getReceiverPublicKey() { return receiverPublicKey; }


}