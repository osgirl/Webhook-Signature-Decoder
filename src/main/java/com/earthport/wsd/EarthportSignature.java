package com.earthport.wsd;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Class used to generate and verify that an Earthport supplied signature for a webhook notification is valid.
 *
 * @author acowell <br />
 * Copyright 2018 Earthport Plc. All rights reserved.
 */
public class EarthportSignature
{
    /**
     * This method is an example of how to verify an Earthport generated webhook signature.
     *
     * @param signature The Earthport generated siganture for this notification.
     * @param payload The payload of the webhook notification in JSON format with all whitespace removed.
     * @param secret The client secret - this is only known by Earthport and by each client.
     * @return true if the signature is valid.
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public boolean verify(String signature, String payload, String secret) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException
    {
        String timestamp = signature.substring(0, signature.lastIndexOf("."));
        
        String signatureWithTimestampRemoved = signature.substring(signature.lastIndexOf(".")+1);

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        String payloadToSign = timestamp + "." + payload;

        String expectedSignature = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(payloadToSign.getBytes("UTF-8")));

        return signatureWithTimestampRemoved.equals(expectedSignature);
    }

    /**
     * This method is an example of how Earthport generates each webhook signature.
     *
     * @param payload The payload of the webhook notification in JSON format with all whitespace removed.
     * @param timestamp A long representing the timestamp in milliseconds since EPOCH.
     * @param secret The client secret - this is only known by Earthport and by each client.
     * @return The Earthport generated Webhook signature.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public String generate(String payload, long timestamp, String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException
    {
        String payloadToSign = timestamp + "." + payload;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        String signature = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(payloadToSign.getBytes("UTF-8")));

        return timestamp + "." + signature;
    }
}
