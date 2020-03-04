/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/*
    The Project class for each project in the database.
*/

@NamedQuery(name = "getAllProjects", query = "SELECT c FROM Project c")
@XmlRootElement(name = "project")
@CascadeOnDelete
@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;
    String description;
    String requiredSkills;

    enum Status {
        Accepted, Proposed, Available
    }
    Status status;

    @OneToOne
    @JoinColumn(name = "project_supervisor")
    Supervisor supervisor;

    @NotNull
    @ManyToMany(targetEntity = ProjectTopic.class)
    List projectTopics;

    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;

    public Project() {

    }

    public Project(String title, String desc, String req, Supervisor sp, List topics) {
        this.title = title;
        description = desc;
        requiredSkills = req;
        status = Status.Available;
        supervisor = sp;
        projectTopics = topics;
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    @XmlAttribute
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonbTransient
    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @XmlAttribute
    public Boolean getAvailable() {
        if (status == status.Available) {
            return true;
        }
        return false;
    }

    public void setProposed() {
        status = status.Proposed;
    }

    public void setAvailable() {
        status = status.Available;
    }

    public void setAccepted() {
        status = status.Accepted;
    }

    @XmlAttribute
    public List getProjectTopics() {
        return projectTopics;
    }

    public void setProjectTopics(List projectTopics) {
        this.projectTopics = projectTopics;
    }

    @JsonbTransient
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
