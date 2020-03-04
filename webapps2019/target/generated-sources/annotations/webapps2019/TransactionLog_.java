package webapps2019;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import webapps2019.SystemUser;
import webapps2019.TransactionLog.transactionType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-09T19:32:10")
@StaticMetamodel(TransactionLog.class)
public class TransactionLog_ { 

    public static volatile SingularAttribute<TransactionLog, Long> id;
    public static volatile SingularAttribute<TransactionLog, Timestamp> transactionTime;
    public static volatile SingularAttribute<TransactionLog, String> transaction_log;
    public static volatile SingularAttribute<TransactionLog, transactionType> TransactionType;
    public static volatile SingularAttribute<TransactionLog, SystemUser> user;

}