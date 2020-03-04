/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.ejb;

import webapps2019.Administrator;
import webapps2019.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import webapps2019.SystemUser;

/*
    The InitialRegistrationBean used for registering the default admin.
*/

@Singleton
public class InitialRegistration {

    @PersistenceContext
    EntityManager em;

    public InitialRegistration() {

    }

    /*
        This method is used for registering the first admin (admin1) into the database if it does not already exist.
    */
    public void registerAdmin() {

        String id = "admin1";
        String password = "password1";

        if (em.find(SystemUser.class, id) == null) {
            try {

                MessageDigest md = MessageDigest.getInstance("SHA-256");

                String passwd = password;

                md.update(passwd.getBytes("UTF-8"));

                byte[] digest = md.digest();

                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }

                String paswdToStoreInDB = sb.toString();
                Administrator user = new Administrator(id, paswdToStoreInDB);

                em.persist(user);
                SystemUserGroup sUserGroup = new SystemUserGroup(id, "Administrator");
                em.persist(sUserGroup);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UserServiceStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
