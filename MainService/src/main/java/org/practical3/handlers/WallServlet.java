package org.practical3.handlers;

import org.practical3.model.postService.AnswerPostService;
import org.practical3.model.postService.PostField;
import org.practical3.model.postService.RequestWall;
import org.practical3.utils.Commons;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


public class WallServlet extends HttpServlet {

    Commons Common;

    public WallServlet(Commons commons) {
        Common = commons;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            getPosts(req,resp);


        }
        catch (ClassNotFoundException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: no posts found for provided ids",HttpServletResponse.SC_NOT_FOUND)));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: wrong arguments",HttpServletResponse.SC_BAD_REQUEST)));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: internal server error:"+e.getMessage(),HttpServletResponse.SC_INTERNAL_SERVER_ERROR)));

        }



    }

    private void getPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String,String[]> args =  req.getParameterMap();
        String UserName = args.get("username")[0];

        //получить по юзернейму id

        //получить по id подписки
        //получить по id подписок посты
        Collection<Integer> ids = new ArrayList<Integer>();
        Collection<PostField> postFields =RequestWall.parseFields(args);
        Date dateAfter = new Date( args.get("dateAfter")[0]);
        Date DateBefore = new Date( args.get("dateBefore")[0]);
        Integer Count = new Integer(args.get("Count")[0]);
        Integer Offset = new Integer( args.get("Offset")[0]);
        RequestWall requestWall = new RequestWall(ids
                ,postFields
                ,dateAfter
                ,DateBefore
                ,Count
                ,Offset);
        AnswerPostService postServiceAnswer = Common.httpGetWall(requestWall);

        doReply(postServiceAnswer, resp);
    }

    private void doReply(AnswerPostService postServiceAnswer, HttpServletResponse resp) throws Exception {
        resp.setContentType("application/json");
        resp.setStatus(postServiceAnswer.Code);
        resp.getWriter().println(Common.gson.toJson(postServiceAnswer));
    }




}