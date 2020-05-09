package work.funcke.filter;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * This filter verify the access permissions for a user
 * based on username and password provided in request
 */
@Provider
public class AuthorizationFilter implements ContainerRequestFilter
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

        //Access denied for all
        if(method.isAnnotationPresent(DenyAll.class))
            deny();

        if(method.isAnnotationPresent(Authorization.class))
        {
            String authorization = requestContext.getHeaderString(AUTHORIZATION_HEADER);
            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                deny();
            }

            Authorization rolesAnnotation = method.getAnnotation(Authorization.class);
            Set<String> accessRights = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            //Get encoded username and password
            final String key = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            if( ! authorize(key, accessRights))
                deny();
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

    private void deny() {
        throw new ForbiddenException("Resource Forbidden");
    }
}