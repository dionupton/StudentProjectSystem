package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.SystemUser;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;
import javax.inject.Inject;

@Named("LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    UserServiceStoreInterface userStore;

    @Inject
    TimeStampBean timeStamp;

    String username;
    String password;
    SystemUser sUser = null;

/*
    Used for logging in and out the currently logged in user. Then returns the user to the index page.
*/       
    public String logout() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            context.addMessage(null, new FacesMessage("User is logged out"));

        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
        sUser = null;
        return "/index";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*
    Checks which user has logged in. It is then reported to the userStore which logs the transaction time.
*/       
    public void checkLoggedIn() {
        if (sUser == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            System.out.println(request.getRemoteUser() + " is logged in");
            sUser = userStore.parseSystemUser(request.getRemoteUser());
            try {
                userStore.Login(sUser, java.sql.Timestamp.valueOf(timeStamp.getTimeStamp()));
            } catch (TException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println(timeStamp.getTimeStamp());
            } catch (TException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
