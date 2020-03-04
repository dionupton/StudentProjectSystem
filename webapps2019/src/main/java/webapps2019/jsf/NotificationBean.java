/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.ProjectRequests;
import webapps2019.Supervisor;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;

/*
    This bean is used for handling the notifications for the supervisor.
*/
@Named("NotificationBean")
@SessionScoped
public class NotificationBean implements Serializable {

    @EJB
    UserServiceStoreInterface userStore;

    @Inject
    TimeStampBean timeStampBean;

    Supervisor currentSupervisor;

    int notificationCount = 0;
    List currentProposals;
    Boolean showNotifications = false;

    ProjectRequests pickedRequest = null;

/*
    Called after the bean is constructed, sets the current supervisor logged in and calls the CheckNotifications method.
*/
    @PostConstruct
    void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();

        currentSupervisor = userStore.parseSupervisor(user);

        CheckNotifications();
    }

/*
    Used to set the number of notifications to the size of the current proposals for this supervisor.
*/    
    void CheckNotifications() {
        //System.out.println(currentSupervisor);
        List proposals = userStore.getSupervisorProposals(currentSupervisor);
        if (proposals != null) {
            notificationCount = proposals.size();

        }
        currentProposals = proposals;
    }

    public List getProposals() {
        return userStore.getSupervisorProposals(currentSupervisor);
    }

    public UserServiceStoreInterface getUserStore() {
        return userStore;
    }

    public void setUserStore(UserServiceStoreInterface userStore) {
        this.userStore = userStore;
    }

/*
    Used by the supervisor logged in the accept the passed project request.
    Uses the EJB to accept the project.
*/    
    public String AcceptRequest(ProjectRequests pr) {

        String r = "";
        try {
            r = userStore.AcceptRequest(pr, java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CheckNotifications();
        showNotifications = false;
        return r;
    }

/*
    Used by the supervisor to reject the passed project request.
    Uses the EJB to reject the project.
*/    
    public String RejectRequest(ProjectRequests pr) {
        String r;
        try {
            r = userStore.RejectRequest(pr, java.sql.Timestamp.valueOf(timeStampBean.getTimeStamp()));
        } catch (TException ex) {
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CheckNotifications();
        showNotifications = false;
        return r = "";
    }

    public Supervisor getCurrentSupervisor() {
        return currentSupervisor;
    }

    public void setCurrentSupervisor(Supervisor currentSupervisor) {
        this.currentSupervisor = currentSupervisor;
    }

    public int getNotificationCount() {
        CheckNotifications();
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

/*
    Used by the web page, returns true if the supervisor has more than 0 notifications.
*/    
    public Boolean hasNotifications() {
        CheckNotifications();
        if (notificationCount > 0 && showNotifications) {
            return true;
        }
        return false;
    }

    public List getCurrentProposals() {
        return currentProposals;
    }

    public void setCurrentProposals(List currentProposals) {
        this.currentProposals = currentProposals;
    }

    public Boolean getShowNotifications() {
        return showNotifications;
    }

    public String setShowNotifications() {
        showNotifications = !showNotifications;
        return "supervisor";
    }

    public ProjectRequests getPickedRequest() {
        return pickedRequest;
    }

    public void setPickedRequest(ProjectRequests pickedRequest) {
        this.pickedRequest = pickedRequest;
    }

}
