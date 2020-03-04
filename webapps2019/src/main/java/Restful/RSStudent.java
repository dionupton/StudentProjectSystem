package Restful;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.Project;
import webapps2019.Supervisor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

// The Singleton annotaion denotes that there will be a single object of the RSEmployee class - don't change that
/**
 * * Annotate the class so that it exports Employee resources under the relative
 * path /employee **
 */
@Singleton
@Path("/student")
public class RSStudent {

    @PersistenceContext
    EntityManager em;

    public RSStudent() {

    }

    @GET
    @Path("/{supervisorid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List getProjects(@PathParam("supervisorid") String supervisorid) {

        List projects = parseSupervisor(supervisorid).getProjects();
        List students = new ArrayList();
        for (Object p : projects) {
            Project proj = (Project) p;
            students.add(proj.getStudent());
        }
        return students;

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllProjects() {
        return getAllStudents();
    }

    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!!");
    }

    public List getAllStudents() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }

    public Supervisor parseSupervisor(String name) {

        return em.find(Supervisor.class, name);
    }

}
