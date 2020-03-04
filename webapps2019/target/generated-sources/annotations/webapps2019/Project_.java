package webapps2019;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import webapps2019.Project.Status;
import webapps2019.ProjectTopic;
import webapps2019.Student;
import webapps2019.Supervisor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-09T19:32:10")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, Long> id;
    public static volatile SingularAttribute<Project, String> title;
    public static volatile ListAttribute<Project, ProjectTopic> projectTopics;
    public static volatile SingularAttribute<Project, Student> student;
    public static volatile SingularAttribute<Project, String> requiredSkills;
    public static volatile SingularAttribute<Project, Status> status;
    public static volatile SingularAttribute<Project, Supervisor> supervisor;
    public static volatile SingularAttribute<Project, String> description;

}