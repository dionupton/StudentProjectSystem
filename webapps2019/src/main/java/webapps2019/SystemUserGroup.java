/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
    The SystemUserGroup class used for managing the types of SystemUsers (Admin, Supervisor, Student)
*/

@Entity
public class SystemUserGroup implements Serializable {

    @Id
    String Username;
    String GroupName;

    public SystemUserGroup() {

    }

    public SystemUserGroup(String username, String groupname) {
        Username = username;
        GroupName = groupname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

}
