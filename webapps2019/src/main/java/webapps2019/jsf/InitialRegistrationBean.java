/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2019.jsf;

import webapps2019.ejb.InitialRegistration;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *  This JSF is deployed when the user first connects to the index page.
 *  It is used to contact the EJB initialRegistration to register the first admin (admin1)
 */

@Named("InitialRegistrationBean")
@RequestScoped
public class InitialRegistrationBean implements Serializable {

    @EJB
    InitialRegistration initialRegistration;

    public InitialRegistrationBean() {

    }

    //called by index page on load
    public void Load() {
        initialRegistration.registerAdmin();
    }
}
