/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.TimeStampServer;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import time.TimeStamper;

/*
    Used for fetching a time stamp from the time stamp server.
*/    

@Named("TimeStampBean")
@RequestScoped
public class TimeStampBean {

    @EJB
    TimeStampServer timeServer;

    TimeStamper.Client client;
    TProtocol protocol;
    TTransport transport;

    public TimeStampBean() {

    }
    
/*
    Returns a time stamp in the format string from the time stamp server.
*/    
    public String getTimeStamp() throws TException {

        System.out.println("Starting timestamp");
        timeServer.wake();

        String result = "";
        try {
            // instantiate a new TTransport protocol (will use a TCP socket)
            transport = new TSocket("localhost", 10000);
            transport.open();

            //instantiate a TProtocol using the TTransport instantiated above
            protocol = new TBinaryProtocol(transport);
            //Finally, instantiate a synchronous client using the TProtocol instantiated above
            client = new TimeStamper.Client(protocol);

            result = client.getTimeStamp();
            System.out.println("RPC Call TimeStamp = " + result);
            transport.close();
        } catch (TException x) {
            System.err.println(x);
        }

        return result;
    }

}
