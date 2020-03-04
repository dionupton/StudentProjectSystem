/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/*
    The Supervisor class for each system supervisor. Inherits from SystemUser.
*/

@NamedQuery(name = "getAllSupervisors", query = "SELECT c FROM Supervisor c")
@XmlRootElement(name = "supervisor")
@Entity
public class Supervisor extends SystemUser {

    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    String department;
    @NotNull
    String email;
    @NotNull
    String tNumber;

    @OneToMany(targetEntity = Project.class)
    List projects;

    public Supervisor(String id, String password, String name, String surname, String department, String email, String tNumber) {
        this.username = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.email = email;
        this.tNumber = tNumber;
        this.password = password;
        projects = new ArrayList();
    }

    public Supervisor() {

    }

    public void addProject(Project p) {
        projects.add(p);
    }

    public void removeProject(Project p) {
        projects.remove(p);
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlAttribute
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @XmlAttribute
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlAttribute
    public String gettNumber() {
        return tNumber;
    }

    public void settNumber(String tNumber) {
        this.tNumber = tNumber;
    }

    @XmlAttribute
    public List getProjects() {
        return projects;
    }

    public void setProjects(List projects) {
        this.projects = projects;
    }

    @XmlAttribute
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.surname);
        hash = 53 * hash + Objects.hashCode(this.department);
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.tNumber);
        hash = 53 * hash + Objects.hashCode(this.projects);
        return hash;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supervisor other = (Supervisor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.department, other.department)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.tNumber, other.tNumber)) {
            return false;
        }
        if (!Objects.equals(this.projects, other.projects)) {
            return false;
        }
        return true;
    }

}
