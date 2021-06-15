package org.practical3.handlers.PostService;

import org.practical3.api.PostServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.RequestReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


public class SearchPostsServlet extends HttpServlet {




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
         ExceptionHandler.execute(req, resp, (req1, resp1)->{
            SearchPostRequest searchRequest =  RequestReader.getBodyAsObject(req, SearchPostRequest.class);
            if(searchRequest.Username!=null)
                searchRequest.UserId = UserServiceAPI.getUsersIds(searchRequest.Username).get(0);

            Collection<Post> result =  PostServiceAPI.searchPosts(searchRequest);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(StaticGson.toJson(result));
        });
    }






}