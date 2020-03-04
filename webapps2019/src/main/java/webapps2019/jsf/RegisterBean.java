/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/*
    Used for registering system users by the admin.
*/    

@Named("RegisterBean")
@RequestScoped
public class RegisterBean {

    @EJB
    UserServiceStoreInterface userStore;

    String username;
    String password;
    String name;
    String surname;
    String email;
    String course;
    String telephoneNumber;
    String department;

/*
    Used for registering a new student. Logic is done by the EJB.
    Lets the admin know if the username is non unique (informed by the EJB)
*/        
    public String registerStudent() {
        String result = userStore.registerStudent(username, password, name, surname, email, course, returnCallerName());
        FacesContext context = FacesContext.getCurrentInstance();
        if ("notunique".equals(result)) {
            context.addMessage(null, new FacesMessage("Username is not unique."));
            return "studentregistration";
        }

        context.addMessage(null, new FacesMessage("Student successfully added"));
        clearAll();
        return "studentregistration";
    }
    
/*
    Used for registering a new supervisor. Logic is done by the EJB.
    Lets the admin know if the username is non unique (informed by the EJB)
*/      
    public String registerSupervisor() {
        String result = userStore.registerSupervisor(username, password, name, surname, department, email, telephoneNumber, returnCallerName());
        FacesContext context = FacesContext.getCurrentInstance();
        if ("notunique".equals(result)) {
            context.addMessage(null, new FacesMessage("Username is not unique."));
            return "supervisorregistration";
        }
        context.addMessage(null, new FacesMessage("Supervisor successfully added"));
        clearAll();
        return "supervisorregistration";
    }

/*
    Used for registering the first admin.
*/          
    public void registerFirstAdmin() {
        userStore.registerAdmin("admin1", "password1", returnCallerName());
    }
    
/*
    Used for registering a new admin. Logic is done by the EJB.
    Lets the admin know if the username is non unique (informed by the EJB)
*/    
    public String registerAdmin() {
        String result = userStore.registerAdmin(username, password, returnCallerName());
        FacesContext context = FacesContext.getCurrentInstance();
        if ("notunique".equals(result)) {
            context.addMessage(null, new FacesMessage("Username is not unique."));
            return "adminregistration";
        }
        context.addMessage(null, new FacesMessage("Admin successfully added"));
        clearAll();
        return "adminregistration";
    }

/*
    Returns the name of the currently logged in user.
*/        
    public String returnCallerName() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String user = request.getRemoteUser();
        return user;
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

    public UserServiceStoreInterface getUserStore() {
        return userStore;
    }

    public void setUserStore(UserServiceStoreInterface userStore) {
        this.userStore = userStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void clearAll() {

        username = "";
        password = "";
        name = "";
        surname = "";
        email = "";
        course = "";
        telephoneNumber = "";
        department = "";
    }
}
