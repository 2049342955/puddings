package com.demo.utils;

import com.alibaba.fastjson.JSON;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.util.Assert;

public class JwtUtil {
    private Signer signer;
    private SignatureVerifier verifier;

    public JwtUtil(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
        this.signer = new RsaSigner((RSAPrivateKey)privateKey);
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        this.verifier = new RsaVerifier(publicKey);
    }

    public String encode(Map<String, Object> content) {
        String jsonStr = JSON.toJSONString(content);
        return JwtHelper.encode(jsonStr, this.signer).getEncoded();
    }

    public Map<String, Object> decode(String token) {
        Jwt jwt = JwtHelper.decodeAndVerify(token, this.verifier);
        String content = jwt.getClaims();
        Map<String, Object> map = (Map)JSON.parse(content);
        return map;
    }
}
