package org.openlcb;

import org.junit.*;

/**
 * @author  Bob Jacobsen   Copyright 2009
 * @version $Revision$
 */
public class ProtocolIdentificationTest  {

    @Test
    public void testDecode0() {
        java.util.List result = ProtocolIdentification.Protocol.decode(0x000000000000L);
        
        Assert.assertEquals("length", 0, result.size());
    }
    
    @Test
    public void testDecode1() {
        java.util.List result = ProtocolIdentification.Protocol.decodeNames(0x800000000000L);
        
        Assert.assertEquals("length", 1, result.size());
        Assert.assertEquals("result 1", "SimpleProtocolSubset", result.get(0));
    }
    
    @Test
    public void testDecode2() {
        java.util.List result = ProtocolIdentification.Protocol.decodeNames(0x880000000000L);
        
        Assert.assertEquals("length", 2, result.size());
        Assert.assertEquals("result 1", "SimpleProtocolSubset", result.get(0));
        Assert.assertEquals("result 2", "Reservation", result.get(1));
    }

    @Test
    public void testDecode3() {
        java.util.List result = ProtocolIdentification.Protocol.decodeNames(0xF01800000000L);
        
        Assert.assertEquals("length", 6, result.size());
        Assert.assertEquals("result 1", "SimpleProtocolSubset", result.get(0));
        Assert.assertEquals("result 2", "Datagram", result.get(1));
        Assert.assertEquals("result 3", "Stream", result.get(2));
        Assert.assertEquals("result 4", "Configuration", result.get(3));
        Assert.assertEquals("result 5", "SNII", result.get(4));
        Assert.assertEquals("result 6", "CDI", result.get(5));
    }

    @Test
    public void testDecode4() {
        java.util.List result = ProtocolIdentification.Protocol.decodeNames(0x000F00000000L);
        
        Assert.assertEquals("length", 4, result.size());
        Assert.assertEquals("result 1", "CDI", result.get(0));
        Assert.assertEquals("result 2", "TractionControl", result.get(1));
        Assert.assertEquals("result 3", "FDI", result.get(2));
        Assert.assertEquals("result 4", "DccCommandStation", result.get(3));
    }

    @Test
    public void testSupports1() {
        ProtocolIdentification.Protocol p = ProtocolIdentification.Protocol.Datagram;
        
        Assert.assertTrue("supports", p.supports(~0));
    }
    
    @Test
    public void testSupports2() {
        ProtocolIdentification.Protocol p = ProtocolIdentification.Protocol.Datagram;
        
        Assert.assertTrue("supports", !p.supports(0));
    }

    @Test
    public void hasProtocol() {
        ProtocolIdentification pi = new ProtocolIdentification(
                new NodeID(new byte[]{2,3,3,4,5,6}),
                new ProtocolIdentificationReplyMessage(
                        new NodeID(new byte[]{1,3,3,4,5,6}),new NodeID(new byte[]{2,3,3,4,5,6}),
                        0x000F00000000L));
        Assert.assertTrue(pi.hasProtocol(ProtocolIdentification.Protocol.ConfigurationDescription));
        Assert.assertFalse(pi.hasProtocol(ProtocolIdentification.Protocol
                .FirmwareUpgrade));
        Assert.assertFalse(pi.hasProtocol(ProtocolIdentification.Protocol
                .FirmwareUpgradeActive));
        Assert.assertFalse(pi.hasProtocol(ProtocolIdentification.Protocol
                .Datagram));
        Assert.assertTrue(pi.hasProtocol(ProtocolIdentification.Protocol.FunctionDescription));
        Assert.assertTrue(pi.hasProtocol(ProtocolIdentification.Protocol.DccCommandStation));
    }
    
    @Test
    public void testCreationFromMessage() {
        ProtocolIdentification pi = new ProtocolIdentification(
                new NodeID(new byte[]{2,3,3,4,5,6}),
                new ProtocolIdentificationReplyMessage(
                    new NodeID(new byte[]{1,3,3,4,5,6}),new NodeID(new byte[]{2,3,3,4,5,6}),
                    0x03));
        Assert.assertTrue((long)0x03 == pi.getValue());
    }
}
