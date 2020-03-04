package Restful;

import webapps2019.ejb.UserServiceStoreInterface;
import webapps2019.Student;
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
@Path("/supervisor")
public class RSSupervisor {

    @PersistenceContext
    EntityManager em;

    public RSSupervisor() {

    }

    @GET
    @Path("/{studentid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjects(@PathParam("studentid") String studentid) {

        return Response.ok(parseStudent(studentid).getProject().getSupervisor()).build();

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllProjects() {

        List supervisors = getAllSupervisors();

        return supervisors;
    }

    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!!");
    }

    public List getAllSupervisors() {
        return em.createNamedQuery("getAllSupervisors").getResultList();
    }

    public Student parseStudent(String name) {
        return em.find(Student.class, name);
    }
}
