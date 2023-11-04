package org.openlcb;

import org.junit.*;

/**
 * @author  Bob Jacobsen   Copyright 2009
 */
public class ProducerConsumerEventReportMessageTest {
    boolean result;
    
    EventID eventID1 = new EventID(new byte[]{1,0,0,0,0,0,1,0});
    EventID eventID2 = new EventID(new byte[]{1,0,0,0,0,0,2,0});
    
    NodeID nodeID1 = new NodeID(new byte[]{1,2,3,4,5,6});
    NodeID nodeID2 = new NodeID(new byte[]{0,0,0,0,0,0});
 
    @Test   
    public void testEqualsSame() {
        Message m1 = new ProducerConsumerEventReportMessage(
                               nodeID1, eventID1 );
        Message m2 = new ProducerConsumerEventReportMessage(
                               nodeID1, eventID1 );
    
        Assert.assertTrue(m1.equals(m2));
    }

    @Test   
    public void testNotEqualsDifferentNode() {
        Message m1 = new ProducerConsumerEventReportMessage(
                                nodeID1, eventID1 );
        Message m2 = new ProducerConsumerEventReportMessage(
                                nodeID2, eventID1 );
    
        Assert.assertTrue( ! m1.equals(m2));
    }

    @Test   
    public void testNotEqualsDifferentEvent() {
        Message m1 = new ProducerConsumerEventReportMessage(
                                nodeID1, eventID1 );
        Message m2 = new ProducerConsumerEventReportMessage(
                                nodeID1, eventID2 );
    
        Assert.assertTrue( ! m1.equals(m2));
    }

    @Test   
    public void testPayload() {
        ProducerConsumerEventReportMessage m1 = new ProducerConsumerEventReportMessage(
                                nodeID1, eventID1 );

        Assert.assertEquals(0, m1.getPayloadSize());
    }

    @Test   
    public void testHandling() {
        result = false;
        Node n = new Node(){
            @Override
            public void handleProducerConsumerEventReport(ProducerConsumerEventReportMessage msg, Connection sender){
                result = true;
            }
        };
        Message m = new ProducerConsumerEventReportMessage(
                                            new NodeID(new byte[]{1,2,3,4,5,6}),
                                            new EventID(new byte[]{1,0,0,0,0,0,3,0}) );
        
        n.put(m, null);
        
        Assert.assertTrue(result);
    }
    
}
