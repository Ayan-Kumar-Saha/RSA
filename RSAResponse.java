import java.math.BigInteger;

public class RSAResponse {

    private BigInteger publicKeyExponent;
    private BigInteger privateKeyExponent;
    private BigInteger modulus;

    public RSAResponse(BigInteger publicKeyExponent, BigInteger modulus) {
        
        this.publicKeyExponent = publicKeyExponent;
        this.modulus = modulus;
    }

    public RSAResponse(BigInteger publicKeyExponent, BigInteger privateKeyExponent, BigInteger modulus) {

        this.publicKeyExponent = publicKeyExponent;
        this.privateKeyExponent = privateKeyExponent;
        this.modulus = modulus;
    }
    
    public void setPublicKeyExponent(BigInteger publicKeyExponent) { this.publicKeyExponent = publicKeyExponent; }

    public void setPrivateKeyExponent(BigInteger privateKeyExponent) { this.privateKeyExponent = privateKeyExponent; }

    public void setModulus(BigInteger modulus) { this.modulus = modulus; }

    public BigInteger getPublicKeyExponent() { return publicKeyExponent; }

    public BigInteger getPrivateKeyExponent() { return privateKeyExponent; }

    public BigInteger getModulus() { return modulus; }
}