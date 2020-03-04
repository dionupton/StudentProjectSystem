/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.Student;
import webapps2019.Supervisor;
import webapps2019.SystemUser;
import webapps2019.TransactionLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
    This JSF is used for listing non-login transactions for the admin.
*/     
@Named("TransactionBean")
@SessionScoped
public class TransactionListingBean implements Serializable {

    @EJB
    UserServiceStoreInterface userStore;

    Supervisor checkedSupervisor;
    SystemUser checkedStudent;
    List supervisorlist;
    List studentlist;

    public List returnStudents() {
        return userStore.getAllStudents();
    }

    public List returnSupervisors() {
        return userStore.getAllSupervisors();
    }
    
/*
    Returns all non-login transaction logs.
    Will return the transaction logs for a specified student or supervisor if they have been checked on the web page.
*/       
    public List returnTransactionLogs() {
        List tl = userStore.getAllTransactionLogs();

        if ((supervisorlist != null && studentlist != null) && (checkedSupervisor != null || checkedStudent != null)) {
            List tmp = new ArrayList();

            for (int i = 0; i < tl.size(); i++) {
                TransactionLog s = (TransactionLog) tl.get(i);
                SystemUser su = (SystemUser) s.getUser();

                if (checkedSupervisor != null) {
                    if (su.getUsername().equals(checkedSupervisor.getUsername())) {
                        tmp.add(s);
                    }
                }
                if (checkedStudent != null) {
                    if (su.getUsername().equals(checkedStudent.getUsername())) {
                        tmp.add(s);
                    }
                }
            }
            return tmp;
        }
        return userStore.getAllTransactionLogs();
    }
    
/*
    Sets the passed supervisor as the checkedSupervisor. This is used for filtering the transaction logs.
*/     
    public String checkSupervisor(String superv) {
        checkedStudent = null;
        checkedSupervisor = userStore.parseSupervisor(superv);
        System.out.println("CALLED" + superv + " - ");
        return "transactionlogs";
    }
/*
    Sets the passed student as the checkedStudent. This is used for filtering the transaction logs.
*/    
    public String checkStudent(String student) {
        checkedSupervisor = null;
        checkedStudent = userStore.parseStudent(student);
        System.out.println("CALLED" + student + " - ");
        return "transactionlogs";
    }
    
/*
    Returns a list of all supervisor usernames for the web page checkbox.
*/       
    public List supervisorcheckbox() {
        supervisorlist = returnSupervisors();
        String[] tmp = new String[supervisorlist.size()];

        for (int i = 0; i < supervisorlist.size(); i++) {
            Supervisor s = (Supervisor) supervisorlist.get(i);
            tmp[i] = s.getUsername();
        }

        return Arrays.asList(tmp);
    }
/*
    Returns a list of all student usernames for the web page checkbox.
*/       
    public List studentcheckbox() {
        studentlist = returnStudents();
        String[] tmp = new String[studentlist.size()];

        for (int i = 0; i < studentlist.size(); i++) {
            Student s = (Student) studentlist.get(i);
            tmp[i] = s.getUsername();
        }

        return Arrays.asList(tmp);
    }

    public List getSupervisorlist() {
        return supervisorlist;
    }

    public void setSupervisorlist(List supervisorlist) {
        this.supervisorlist = supervisorlist;
    }

    public UserServiceStoreInterface getUserStore() {
        return userStore;
    }

    public void setUserStore(UserServiceStoreInterface userStore) {
        this.userStore = userStore;
    }

    public Supervisor getCheckedSupervisor() {
        return checkedSupervisor;
    }

    public void setCheckedSupervisor(Supervisor checkedSupervisor) {
        this.checkedSupervisor = checkedSupervisor;
    }

}
