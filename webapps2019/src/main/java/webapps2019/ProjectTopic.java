/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/*
    The ProjectTopic class for each project topic. A project can have multiple project topics.
*/

@NamedQuery(name = "getAllProjectTopics", query = "SELECT c FROM ProjectTopic c")

@Entity

public class ProjectTopic implements Serializable {


    @Id
    String topictitle;

    String topicdescription;


    public ProjectTopic() {

    }

    public ProjectTopic(String title, String desc) {
        topictitle = title;
        topicdescription = desc;
    }

    public String getTopictitle() {
        return topictitle;
    }

    public void setTopictitle(String topictitle) {
        this.topictitle = topictitle;
    }

    public String getTopicdescription() {
        return topicdescription;
    }

    public void setTopicdescription(String topicdescription) {
        this.topicdescription = topicdescription;
    }

}
