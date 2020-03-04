package Restful;


import webapps2019.Project;
import webapps2019.Supervisor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Singleton
@Path("/project")
public class RSProject {

    @PersistenceContext
    EntityManager em;

    public RSProject() {

    }

    @GET
    @Path("/{supervisorid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List getProjects(@PathParam("supervisorid") String supervisorid) {
        return getSupervisorProjects(parseSupervisor(supervisorid));

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAllProjects() {
        return em.createNamedQuery("getAllProjects").getResultList();
    }

    @PostConstruct
    public void init() {
        
    }

    public List getSupervisorProjects(Supervisor s) {
        List list = em.createNamedQuery("getAllProjects").getResultList();
        List tmpList = new ArrayList();
        s = em.find(Supervisor.class, s.getUsername());
        for (Object pp : list) {
            Project proj = (Project) pp;
            if (proj.getSupervisor().equals(s)) {
                tmpList.add(proj);
            }
        }
        return tmpList;
    }

    public Supervisor parseSupervisor(String name) {

        return em.find(Supervisor.class, name);
    }

}
