package org.fisco.bcos.client;

import org.junit.Test;

import javax.security.sasl.SaslServer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class BackClientTest
{
    @Test
    public void main() throws Exception
    {
        BackClient client = new BackClient();
        client.initialize();
        client.deployBackAndRecordAddr();
        client.insertUser(BigInteger.valueOf(1),"A".getBytes(StandardCharsets.UTF_8));
        client.insertUser(BigInteger.valueOf(2),"B".getBytes(StandardCharsets.UTF_8));
        System.out.println(client.getAllUsers());
    }
}