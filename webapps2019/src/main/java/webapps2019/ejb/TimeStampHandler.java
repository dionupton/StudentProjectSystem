/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.ejb;

import java.sql.Timestamp;
import org.apache.thrift.TException;
import time.TimeStamper;

/*
    The handler for the time stamp server
*/
public class TimeStampHandler implements TimeStamper.Iface {

    @Override
    public String getTimeStamp() throws TException {

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        return timeStamp.toString();
    }

}
