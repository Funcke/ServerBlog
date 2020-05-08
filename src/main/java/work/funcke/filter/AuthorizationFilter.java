package work.funcke.filter;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 */
@Provider
public class AuthorizationFilter implements javax.ws.rs.container.ContainerRequestFilter
{

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private static final Map<String, Set<String>> userData = new HashMap<String, Set<String>>() {{
        put("admin", new HashSet<String>(){{
            add("example:read");
        }});
        put("example_user", new HashSet<String>() {{
            add("example:write");
        }});
    }};

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        Method method = resourceInfo.getResourceMethod();

        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
            return;
        //Access denied for all
        if(method.isAnnotationPresent(DenyAll.class))
            deny(requestContext);

        if(method.isAnnotationPresent(Authorization.class))
        {
            Authorization rolesAnnotation = method.getAnnotation(Authorization.class);
            Set<String> accessRights = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_HEADER);

            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                deny(requestContext);
                return;
            }

            //Get encoded username and password
            final String key = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            if( ! authorize(key, accessRights))
                deny(requestContext);
        }
    }

    private boolean authorize(final String key, final Set<String> accessRights)
    {
        boolean isAllowed = false;
        Set<String> permittedActions = userData.get(key);

        if(permittedActions != null && permittedActions.stream().anyMatch(accessRights::contains))
            isAllowed = true;

        return isAllowed;
    }

    private void deny(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("You cannot access this resource").build());
    }
}