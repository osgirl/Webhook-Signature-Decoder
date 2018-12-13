package com.earthport.wsd;

import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Unit tests verifying the Earthport Signature which is supplied as an HTTP HEADER in each webhook notification.
 *
 * @author acowell <br />
 * Copyright 2018 Earthport Plc. All rights reserved.
 */
public class EarthportSignatureTests
{
    /**
     * This would be sent in the notification request as an HTTP HEADER 'client-id'.
     * This is useful when the client has multiple client-id and secrets: possibly due to different environments (sandbox and production).
     * And therefore can use this to lookup their correct secret.
     */
    private static final String CLIENT_ID = "y9OJrEnfV6ZHFospsQSeSiJqhfaUD6lH";

    /**
     * This is private/secret per client. Each client would need to look this up.
     */
    private static final String CLIENT_SECRET = "mrGmAjG3uy3YLs83";

    /**
     *  This would be the value stored in the HTTP HEADER 'earthport-signature'.
     *  The structure of the signature is <TIMESTAMP>.<SIGNATURE>
     */
    private static final String EARTHPORT_SIGNATURE = "1544701184264.V38kC60MSXVqZtcNLMr0upPvv0gBPEKlIVfLB0wB1kI=";

    private EarthportSignature earthportSignature = new EarthportSignature();

    @Test
    public void decodeSignature() throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException
    {
        String payload = loadNotificationFromFile("refundNotification.json");

        assertTrue( earthportSignature.verify(EARTHPORT_SIGNATURE, payload, CLIENT_SECRET) );
    }

    @Test
    public void generateSignature() throws NoSuchAlgorithmException, InvalidKeyException, IOException, URISyntaxException
    {
        String payload = loadNotificationFromFile("refundNotification.json");

        String signature = earthportSignature.generate(payload, 1544701184264L, CLIENT_SECRET);
        assertEquals(EARTHPORT_SIGNATURE, signature);
    }

    private String loadNotificationFromFile(String filename) throws URISyntaxException, IOException
    {
        Path path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());

        byte[] fileBytes = Files.readAllBytes(path);

        return new String(fileBytes);
    }
}
