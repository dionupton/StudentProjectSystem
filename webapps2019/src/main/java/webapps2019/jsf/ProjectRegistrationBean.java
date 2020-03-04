/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.ProjectTopic;
import webapps2019.Supervisor;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;


/*
    Used for registering projects / project requests and topics
*/

@Named("ProjectBean")
@RequestScoped

public class ProjectRegistrationBean {

    @EJB
    UserServiceStoreInterface userStore;

    @Inject
    TimeStampBean timeStampBean;

    String title;
    String description;
    String requiredSkills;
    String[] projectTopics;

/*
    Used by either an administrator or supervisor to register a new project topic.
    EJB handles the actual registering.
*/    
    public String registerNewProjectTopic() {
        try {
            userStore.registerProjectTopic(title, description, getCurrentUser(), java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(ProjectRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Project Topic successfully added"));
        title = "";
        description = "";
        return "topicregistration";
    }

/*
    Used by the supervisor to register a new project.
    EJB handles the actual registering.
*/    
    public String registerNewProject() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        try {
            userStore.registerProject(title, description, requiredSkills, user, projectTopics, getCurrentUser(), java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(ProjectRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        context.addMessage(null, new FacesMessage("Project successfully added by " + user));

        title = "";
        description = "";
        requiredSkills = "";
        return "projectregistration";
    }
    
/*
    Used by a student to register and request a new project.
    EJB handles the actual registering.
*/    
    public String registerNewProject(Supervisor s) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        try {
            userStore.registerandRequestProject(title, description, requiredSkills, s, projectTopics, user, java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(ProjectRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        context.addMessage(null, new FacesMessage("Project successfully requested by " + user));

        return "supervisorselection";
    }

/*
    Returns all the project topic titles for selection on the web page.
*/        
    public String[] getProjectTopicValue() {
        List topicList = userStore.getAllProjectTopics();
        projectTopics = new String[topicList.size()];
        int i = 0;

        for (Object topic : topicList) {
            ProjectTopic req = (ProjectTopic) topic;
            projectTopics[i] = req.getTopictitle();
            i++;
        }
        return projectTopics;
    }

    public String getProjectTopicsInString() {
        return Arrays.toString(projectTopics);
    }

    public UserServiceStoreInterface getUserStore() {
        return userStore;
    }

    public void setUserStore(UserServiceStoreInterface userStore) {
        this.userStore = userStore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String[] getProjectTopics() {
        return projectTopics;
    }

    public void setProjectTopics(String[] projectTopics) {
        this.projectTopics = projectTopics;
    }

 /*
    Returns current logged in user.
*/       
    public String getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();
        return user;
    }
}
