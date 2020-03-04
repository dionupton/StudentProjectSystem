package webapps2019;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import webapps2019.Project;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-09T19:32:10")
@StaticMetamodel(Supervisor.class)
public class Supervisor_ extends SystemUser_ {

    public static volatile ListAttribute<Supervisor, Project> projects;
    public static volatile SingularAttribute<Supervisor, String> email;
    public static volatile SingularAttribute<Supervisor, String> department;
    public static volatile SingularAttribute<Supervisor, String> name;
    public static volatile SingularAttribute<Supervisor, String> surname;
    public static volatile SingularAttribute<Supervisor, String> tNumber;

}