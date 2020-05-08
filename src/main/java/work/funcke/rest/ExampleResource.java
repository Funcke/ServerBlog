package work.funcke.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import work.funcke.data.Repository;
import work.funcke.data.User;
import work.funcke.filter.Authorization;
import work.funcke.messages.AdministrationInterfacePublisher;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
public class ExampleResource {
    private Repository<User> repo = new Repository<>(User.class);
    //@Inject
    //private AdministrationInterfacePublisher publisher;

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public User hello(@FormParam("username")String username) {
        User c = new User();
        c.setUsername(username);
        System.err.println(repo.entityManager == null);
        repo.entityManager.getTransaction().begin();
        repo.entityManager.persist(c);
        repo.entityManager.getTransaction().commit();
        return  c;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorization("example:read")
    public List<User> getAll() throws Exception{
        //this.publisher.setRoutingKey("users");
        //this.publisher.publishMessage("new User: " + new ObjectMapper().writeValueAsString(new User()));
        this.repo.create(new User());
        return repo.all();
    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        return "<h1>hello World</h1>";
    }
}
