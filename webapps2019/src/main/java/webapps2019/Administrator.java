/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019;

import javax.persistence.Entity;

/*
    The Administrator class for each system administrator. Inherits from SystemUser.
*/

@Entity
public class Administrator extends SystemUser {

    public Administrator() {

    }

    public Administrator(String id, String password) {
        this.username = id;
        this.password = password;

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

}
