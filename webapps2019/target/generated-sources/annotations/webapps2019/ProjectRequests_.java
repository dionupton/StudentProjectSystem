package webapps2019;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import webapps2019.Project;
import webapps2019.Student;
import webapps2019.Supervisor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-09T19:32:10")
@StaticMetamodel(ProjectRequests.class)
public class ProjectRequests_ { 

    public static volatile SingularAttribute<ProjectRequests, Long> id;
    public static volatile SingularAttribute<ProjectRequests, Project> project;
    public static volatile SingularAttribute<ProjectRequests, Student> student;
    public static volatile SingularAttribute<ProjectRequests, Supervisor> supervisor;

}