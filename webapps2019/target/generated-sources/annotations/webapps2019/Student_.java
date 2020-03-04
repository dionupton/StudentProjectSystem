package webapps2019;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import webapps2019.Project;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-09T19:32:10")
@StaticMetamodel(Student.class)
public class Student_ extends SystemUser_ {

    public static volatile SingularAttribute<Student, String> course;
    public static volatile SingularAttribute<Student, Project> project;
    public static volatile SingularAttribute<Student, String> email;
    public static volatile SingularAttribute<Student, String> name;
    public static volatile SingularAttribute<Student, Project> requestedProject;
    public static volatile SingularAttribute<Student, String> surname;

}