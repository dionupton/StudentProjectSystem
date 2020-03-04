/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.ejb;

import webapps2019.Project;
import webapps2019.ProjectRequests;
import webapps2019.Student;
import webapps2019.Supervisor;
import webapps2019.SystemUser;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserServiceStoreInterface {

    public String registerStudent(String id, String password, String name, String surname, String email, String course, String caller);

    public String registerSupervisor(String id, String password, String name, String surname, String department, String email, String tNumber, String caller);

    public String registerAdmin(String id, String password, String caller);

    public void registerProjectTopic(String title, String description, String caller, Timestamp time);

    public List getAllStudents();

    public List getAllProjects();

    public List getAllSupervisors();

    public List getAllProjectTopics();

    public List getAllTransactionLogs();

    public List getAllTransactionLogins();

    public List getSupervisorProjects(Supervisor s);

    public void registerProject(String title, String description, String rSkills, String sup, String[] topics, String caller, Timestamp time);

    public void registerandRequestProject(String title, String description, String rSkills, Supervisor sup, String[] topics, String student, Timestamp time);

    public Supervisor parseSupervisor(String name);

    public void RequestProject(Project p, String s, Timestamp time);

    public Boolean StudentHasRequest(String n);

    public void PersistProject(Project p);

    public List getSupervisorProposals(Supervisor s);

    public String AcceptRequest(ProjectRequests pr, Timestamp time);

    public String RejectRequest(ProjectRequests pr, Timestamp time);

    public Student parseStudent(String name);

    public SystemUser parseSystemUser(String name);

    public void Login(SystemUser su, Timestamp time);

    public void RemoveProject(Student student);
}
