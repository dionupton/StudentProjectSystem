/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.ejb;

import webapps2019.Administrator;
import webapps2019.ProjectRequests;
import webapps2019.Project;
import webapps2019.ProjectTopic;
import webapps2019.Student;
import webapps2019.Supervisor;
import webapps2019.SystemUser;
import webapps2019.SystemUserGroup;
import webapps2019.TransactionLog;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/*
    The main EJB Bean for all business logic for the system.
*/
@DeclareRoles({"Administrator", "Supervisor", "Student"})
@Stateless
public class UserServiceStore implements UserServiceStoreInterface {

    @PersistenceContext
    EntityManager em;

    public UserServiceStore() {

    }

/*
    Uses the passed arguments to create a new student entity and persist it in the database.
    If user id is not unique the student is not created.
*/
    @RolesAllowed({"Administrator"})
    public String registerStudent(String id, String password, String name, String surname, String email, String course, String caller) {
        //make sure id is unique
        SystemUser s = parseSystemUser(id);
        if (s != null) {
            return "notunique";
        }
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();
            Student user = new Student(id, paswdToStoreInDB, name, surname, email, course);
            em.persist(user);
            SystemUserGroup sUserGroup = new SystemUserGroup(id, "Student");
            em.persist(sUserGroup);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
    }

/*
    Uses the passed arguments to create a new supervisor entity and persist it in the database.
    If user id is not unique the supervisor is not created.
*/
    @RolesAllowed({"Administrator"})
    public String registerSupervisor(String id, String password, String name, String surname, String department, String email, String teleNumber, String caller) {
        SystemUser s = parseSystemUser(id);
        if (s != null) {
            return "notunique";
        }
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String passwd = password;

            md.update(passwd.getBytes("UTF-8"));

            byte[] digest = md.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            String paswdToStoreInDB = sb.toString();
            Supervisor user = new Supervisor(id, paswdToStoreInDB, name, surname, department, email, teleNumber);

            em.persist(user);
            SystemUserGroup sUserGroup = new SystemUserGroup(id, "Supervisor");
            em.persist(sUserGroup);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
    }

/*
    Uses the passed arguments to create a new administrator entity and persist it in the database.
    If user id is not unique the administrator is not created.
*/
    
    @RolesAllowed({"Administrator"})
    public String registerAdmin(String id, String password, String caller) {
        SystemUser s = parseSystemUser(id);
        if (s != null) {
            return "notunique";
        }
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String passwd = password;

            md.update(passwd.getBytes("UTF-8"));

            byte[] digest = md.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            String paswdToStoreInDB = sb.toString();
            Administrator user = new Administrator(id, paswdToStoreInDB);

            em.persist(user);
            SystemUserGroup sUserGroup = new SystemUserGroup(id, "Administrator");
            em.persist(sUserGroup);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
    }

/*
    Uses the passed arguments to create a new projectTopic entity and persist it in the database.
*/    
    @RolesAllowed({"Administrator", "Supervisor"})
    public void registerProjectTopic(String title, String description, String caller, Timestamp time) {
        ProjectTopic projectTopic = new ProjectTopic(title, description);
        em.persist(projectTopic);
        if (parseSupervisor(caller) != null) {
            LogTransactionAction("Project Topic " + title + " registered", parseSupervisor(caller), time);
        }
    }

/*
    Uses the passed arguments to create a new Project entity and persist it in the database.
*/     
    @RolesAllowed({"Supervisor"})
    public void registerProject(String title, String description, String rSkills, String sup, String[] topics, String caller, Timestamp time) {
        Supervisor s = parseSupervisor(sup);
        List parsedTopics = new ArrayList();
        for (String top : topics) {
            ProjectTopic foundTopic = em.find(ProjectTopic.class, top);
            parsedTopics.add(foundTopic);

        }

        Project project = new Project(title, description, rSkills, s, parsedTopics);
        em.persist(project);
        LogTransactionAction("Project " + project.getId() + " registered", parseSystemUser(caller), time);

    }

/*
    Used when a student proposes a new project to a selected supervisor.
    Uses the passed arguments to create a new Project and also add this project as a request to the passed supervisor.
    The project is persisted and a new project request is made using the RequestProject method.
*/     
    @RolesAllowed({"Student"})
    public void registerandRequestProject(String title, String description, String rSkills, Supervisor sup, String[] topics, String student, Timestamp time) {
        Supervisor s = em.find(Supervisor.class, sup.getUsername());
        List parsedTopics = new ArrayList();
        for (String top : topics) {
            ProjectTopic foundTopic = em.find(ProjectTopic.class, top);
            parsedTopics.add(foundTopic);

        }

        Project project = new Project(title, description, rSkills, s, parsedTopics);
        project.setProposed();
        em.persist(project);
        RequestProject(em.find(Project.class, project.getId()), student, time, true);
        LogTransactionAction("Project created and proposed to " + s.getUsername(), parseStudent(student), time);

    }

/*
    Returns all students in the database using a named query.
*/     
    @RolesAllowed({"Administrator", "Supervisor"})
    public List getAllStudents() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }

/*
    Returns all projects in the database using a named query.
*/       
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public List getAllProjects() {
        return em.createNamedQuery("getAllProjects").getResultList();
    }

/*
    Returns all supervisors in the database using a named query.
*/       
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public List getAllSupervisors() {
        return em.createNamedQuery("getAllSupervisors").getResultList();
    }

/*
    Returns all projectTopics in the database using a named query.
*/       
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public List getAllProjectTopics() {
        return em.createNamedQuery("getAllProjectTopics").getResultList();
    }

/*
    Returns all non-login transactions.
    Returns all TransactionLogs with the transactionType 'ACTION' in the database using a named query.
    This is for all Transactions that are not 'LOGIN' transactions.
*/       
    @RolesAllowed({"Administrator"})
    public List getAllTransactionLogs() {

        List ts = em.createNamedQuery("getAllTransactionLogs").getResultList();
        List formedList = new ArrayList();
        for (Object o : ts) {
            TransactionLog tl = (TransactionLog) o;
            if (tl.getTransactionType() == tl.returnAction()) {
                formedList.add(tl);
            }
        }

        return formedList;
    }

/*
    Returns all login transactions.
    Returns all TransactionLogs with the transactionType 'LOGIN' in the database using a named query.
    This is for all Transactions that are not 'ACTION' transactions.
*/       
    @RolesAllowed({"Administrator"})
    public List getAllTransactionLogins() {
        List ts = em.createNamedQuery("getAllTransactionLogs").getResultList();
        List formedList = new ArrayList();
        for (Object o : ts) {
            TransactionLog tl = (TransactionLog) o;
            if (tl.getTransactionType() == tl.returnLogin()) {
                formedList.add(tl);
            }
        }

        return formedList;
    }

/*
    Takes a string 'name' and searches for a match to a supervisor ID in the entity manager.
    Returns the match if the supervisor exists.
*/        
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public Supervisor parseSupervisor(String name) {

        return em.find(Supervisor.class, name);
    }

/*
    Takes the passed project and studentid as a string to request the project for the student.
    Takes the project and the student and creates a new and persists a new ProjectRequest
*/       
    @RolesAllowed("Student")
    public void RequestProject(Project p, String s, Timestamp time) {
        ProjectRequests request = new ProjectRequests(p, parseStudent(s), p.getSupervisor());
        Project project = em.find(Project.class, p.getId());
        project.setProposed();
        em.persist(request);
        parseStudent(s).setRequestedProject(p);
        LogTransactionAction("Project " + p.getId() + " requested ", parseStudent(s), time);
    }

/*
    Has the same functionality as RequestProject, only does not log the transaction as this is part of the register and request transaction.
    
    Takes a string 'name' and searches for a match to a supervisor ID in the entity manager.
    Returns the match if the supervisor exists.
*/       
    @RolesAllowed("Student")
    public void RequestProject(Project p, String s, Timestamp time, Boolean t) {
        ProjectRequests request = new ProjectRequests(p, parseStudent(s), p.getSupervisor());
        Project project = em.find(Project.class, p.getId());
        project.setProposed();
        em.persist(request);
        parseStudent(s).setRequestedProject(p);
    }

/*
    Returns true or false depending on if a student in the database has a project/requested project or no project.
    Used to prevent a student requesting a project when they already have one.
*/       
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public Boolean StudentHasRequest(String n) {
        Project p = em.find(Student.class, n).getRequestedProject();
        if (p != null) {
            return false;
        }
        Project pp = em.find(Student.class, n).getProject();
        if(pp != null){
             return false;
        }
        return true;
    }

/*
    Takes a string 'name' and searches for a match to a Student ID in the entity manager.
    Returns the match if the student exists.
*/  
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public Student parseStudent(String name) {
        return em.find(Student.class, name);
    }

/*
    Takes a string 'name' and searches for a match to any SystemUser ID in the entity manager.
    Returns the match if the SystemUser exists.
*/      
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public SystemUser parseSystemUser(String name) {
        return em.find(SystemUser.class, name);
    }

/*
    Simply takes the passed project p and persists it in the entity manager.
*/      
    @RolesAllowed({"Student", "Supervisor"})
    public void PersistProject(Project p) {
        em.persist(p);
    }

/*
    returns all project proposals for a passed supervisor.
    It does this by iterating over all ProjectRequests and finding matches to the supervisor.
*/      
    @RolesAllowed({"Administrator", "Supervisor"})
    public List getSupervisorProposals(Supervisor s) {
        List list = em.createNamedQuery("getAllProposals").getResultList();
        List tmpList = new ArrayList();
        s = em.find(Supervisor.class, s.getUsername());
        for (Object pp : list) {
            ProjectRequests req = (ProjectRequests) pp;
            if (req.getSupervisor().equals(s)) {
                tmpList.add(req);
            }
        }
        return tmpList;
    }

/*
    returns all projects for a passed supervisor.
    It does this by iterating over all Projects and finding matches to the supervisor.
*/      
    @RolesAllowed({"Administrator", "Supervisor"})
    public List getSupervisorProjects(Supervisor s) {
        List list = em.createNamedQuery("getAllProjects").getResultList();
        List tmpList = new ArrayList();
        s = em.find(Supervisor.class, s.getUsername());
        for (Object pp : list) {
            Project proj = (Project) pp;
            if (proj.getSupervisor().equals(s)) {
                tmpList.add(proj);
            }
        }
        return tmpList;
    }

/*
    This method accepts a passed ProjectRequest.
    It takes the ProjectRequest then parses the supervisor, project and student from it. 
    It then uses these to accept the project, assign the projects to the supervisor and student then delete the project request.
*/     
    @RolesAllowed("Supervisor")
    public String AcceptRequest(ProjectRequests pr, Timestamp time) {

        System.out.println(pr.getSupervisor().getName());
        Supervisor s = em.find(Supervisor.class, pr.getSupervisor().getUsername());

        System.out.println(s.getName());

        if(s.getProjects().contains(em.find(Project.class, pr.getProject().getId()))){
            
        }else{
            s.addProject(em.find(Project.class, pr.getProject().getId()));
        }
        

        Student st = em.find(Student.class, pr.getStudent().getUsername());

        st.setProject(pr.getProject());
        st.setRequestedProject(null);

        em.find(Project.class, pr.getProject().getId()).setAccepted();
        em.find(Project.class, pr.getProject().getId()).setStudent(st);
        em.remove(em.find(ProjectRequests.class, pr.getId()));
        LogTransactionAction("Project " + pr.getProject().getId() + " accepted", s, time);
        return "supervisor";
    }

/*
    This function rejects a request, it is called by a supervisor.
    It deletes the projectrequest, sets the project available again and deletes the student from the project.
*/     
    @RolesAllowed("Supervisor")
    public String RejectRequest(ProjectRequests pr, Timestamp time) {

        Supervisor s = em.find(Supervisor.class, pr.getSupervisor().getUsername());
        Student st = em.find(Student.class, pr.getStudent().getUsername());

        st.setRequestedProject(null);
        em.find(Project.class, pr.getProject().getId()).setAvailable();
        em.find(Project.class, pr.getProject().getId()).setStudent(null);
        em.remove(em.find(ProjectRequests.class, pr.getId()));
        LogTransactionAction("Project " + pr.getProject().getId() + " rejected ", s, time);
        return "supervisor";
    }

/*
    Takes the passed string s, a systemuser and timestamp to create a new transactionLog of type ACTION.
    This transaction log is then persisted.
*/     
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public void LogTransactionAction(String s, SystemUser su, Timestamp time) {
        TransactionLog tl = new TransactionLog(s, su, time);
        em.persist(tl);
    }

/*
    Takes the passed string s, a systemuser and timestamp to create a new transactionLog of type LOGIN.
    This transaction log is then persisted.
*/     
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public void LogTransactionLogin(String s, SystemUser su, Timestamp time) {
        TransactionLog tl = new TransactionLog(s, su, time, true);
        em.persist(tl);
    }

/*
    Called when a systemuser logs in. Takes the SystemUser su and Timestamp time passed to log this login transaction.
*/       
    @RolesAllowed({"Student", "Administrator", "Supervisor"})
    public void Login(SystemUser su, Timestamp time) {
        LogTransactionLogin("Logged in", su, time);

    }

/*
    Used by the administrator to remove a project from a passed student.
    If the student has a project, it is removed and set back to available.
    If the student has a proposed project, it is removed and the project is set to available as well as the project request being deleted.
*/       
    @RolesAllowed("Administrator")
    public void RemoveProject(Student student) {

        if (student.getProject() != null) {

            Project sProject = em.find(Project.class, student.getProject().getId());
            Student st = em.find(Student.class, student.getUsername());
            st.setProject(null);

            sProject.setAvailable();
            sProject.setStudent(null);

        }

        if (student.getRequestedProject() != null) {

            List projRequests = em.createNamedQuery("getAllProposals").getResultList();
            ProjectRequests tRequest = null;

            for (Object o : projRequests) {

                ProjectRequests pr = (ProjectRequests) o;
                System.out.println(pr.getStudent().getUsername() + " ~ " + student.getUsername());
                if (pr.getStudent().getUsername().equals(student.getUsername())) {
                    tRequest = pr;
                }
            }

            tRequest = em.find(ProjectRequests.class, tRequest.getId());
            em.remove(tRequest);
            
            Project reqProj = em.find(Project.class, student.getRequestedProject().getId());
            reqProj.setAvailable();
            reqProj.setStudent(null);


            student = em.find(Student.class, student.getUsername());
            student.setRequestedProject(null);

        }
    }

}
