/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/*
    The Student class for each system student. Inherits from SystemUser.
*/

@NamedQuery(name = "getAllStudents", query = "SELECT c FROM Student c")

@XmlRootElement(name = "student")
@Entity
public class Student extends SystemUser {

    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    String email;
    @NotNull
    String course;

    @OneToOne
    Project requestedProject;

    @OneToOne
    Project project;

    public Student() {

    }

    public Student(String id, String password, String name, String surname, String email, String course) {
        this.username = id;
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.email = email;
        this.password = password;
        project = null;
        requestedProject = null;

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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlAttribute
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    @JsonbTransient
    public Project getRequestedProject() {
        return requestedProject;
    }

    public void setRequestedProject(Project requestedProject) {
        this.requestedProject = requestedProject;
    }

    @JsonbTransient
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
