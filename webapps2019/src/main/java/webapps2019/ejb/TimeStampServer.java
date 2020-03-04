/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.ejb;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import time.TimeStamper;

/*
    The TimeStamp server bean. Handles the time stamping thread.
*/

@Singleton
public class TimeStampServer implements Serializable {

    public static TimeStampHandler handler;
    public static TimeStamper.Processor processor;
    public static TServerTransport serverTransport;
    public static TServer server;

    public TimeStampServer() {

    }

    @PostConstruct
    public void main() {
        try {
            handler = new TimeStampHandler();
            processor = new TimeStamper.Processor(handler);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
            server.stop();

        } catch (Exception x) {
            
        }
    }

    /*
        Creates the thread.
    */
    public static void simple(TimeStamper.Processor processor) {
        try {
            serverTransport = new TServerSocket(10000);
            server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the time stamp server at thread " + Thread.currentThread().getId());
            server.serve();
        } catch (Exception e) {
            
        }
    }

    public void wake() {

    }
}
