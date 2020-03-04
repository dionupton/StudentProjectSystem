/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.Project;
import webapps2019.Student;
import webapps2019.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;

/**
 *  This JSF is used for requesting projects as well as lists of supervisors/students and their projects for admins and students.
 *  
 */
@Named("ListingBean")
@SessionScoped
public class ListingBean implements Serializable {

    @EJB
    UserServiceStoreInterface userStore;

    @Inject
    TimeStampBean timeStampBean;

    Project selectedProject;
    Supervisor selectedSupervisor;

    String projectTitle;
    String projectDescription;
    String projectSkills;

    public List returnStudents() {
        return userStore.getAllStudents();
    }

    public List returnProjects() {
        return userStore.getAllProjects();
    }

    public List returnRequests() {
        return userStore.getSupervisorProposals(selectedSupervisor);
    }

    public List returnSupervisors() {
        return userStore.getAllSupervisors();
    }

    public List returnProjects(Supervisor s) {
        return userStore.getSupervisorProjects(s);
    }
    
    public List returnManagedProjects(Supervisor s) {
        List t = userStore.getSupervisorProjects(s);
        List r = new ArrayList();
        for(Object o : t){
            Project p = (Project)o;
            if(p.getStudent() != null){
                r.add(p);
            }
        }
        return r;
    }
    
    /*
        Executed from a JSF page. selectedSupervisor becomes the supervisor passed.
    */
    
    public String SelectSupervisor(Supervisor s) {
        selectedSupervisor = s;
        return "supervisorselection";
    }

    
    //Called when a student requests a project from the appropriate xhtml page. 
    //It works by getting the currently logged in student as well as the project selection from the xhtml page,
    //Then passing this to the EJB which will handle the actual project request
    public String SelectProject() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        context.addMessage(null, new FacesMessage("Project successfully requested by " + user + " " + selectedProject.getTitle()));

        try {

            userStore.RequestProject(selectedProject, user, java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(ListingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        selectedProject = null;
        return "projectselection";
    }

    /*
        Used by a supervisor to get all projects assigned to them from the EJB
    */
    public List returnSupervisorProjects() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        selectedSupervisor = userStore.parseSupervisor(user);
        return returnProjects(selectedSupervisor);
    }
    /*
        Used by a supervisor to get all accepted projects assigned to them from EJB
    */
    public List returnManagedSupervisorProjects() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        selectedSupervisor = userStore.parseSupervisor(user);
        return returnManagedProjects(selectedSupervisor);
    }
    /*
        Simply checks if selectedProject is null or not
    */
    public Boolean checked() {
        if (selectedProject != null) {
            return true;
        }
        return false;
    }
    
    /*
        Simply checks if selectedSupervisor is null or not
    */
    public Boolean supervisorChecked() {
        if (selectedSupervisor != null) {
            return true;
        }
        return false;
    }

    /*
        Simply checks if selectedSupervisor is null or not
    */
    public Boolean hasRequestedProject() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        return userStore.StudentHasRequest(user);
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;

    }

    public Supervisor getSelectedSupervisor() {
        return selectedSupervisor;
    }

    public void setSelectedSupervisor(Supervisor selectedSupervisor) {

        this.selectedSupervisor = selectedSupervisor;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectSkills() {
        return projectSkills;
    }

    public void setProjectSkills(String projectSkills) {
        this.projectSkills = projectSkills;
    }

    /*
        Removes the project of the selected student using the EJB
    */
    public void removeProject(Student student) {
        userStore.RemoveProject(student);
    }

    /*
        Checks if a passed student has any project or requested project
    */
    public Boolean hasProject(Student student) {
        if (student.getRequestedProject() == null && student.getProject() == null) {
            return false;
        }

        return true;
    }

}
