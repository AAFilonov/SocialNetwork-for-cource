package org.practical3.utils;


import org.practical3.model.transfer.Answer;

;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Commons {

    public static Collection<Integer> parseIds(String IdsString) {
        String[] post_ids_s = IdsString.split(",");
        ArrayList<Integer> ids = new ArrayList<>();
        for (String id : post_ids_s) {
            ids.add(new Integer(id));
        }
        return ids;
    }






/*
    public static void executeAndCatchExceptions(HttpServletRequest req, HttpServletResponse resp, RequestProcessor processor) throws IOException {
        resp.setContentType("application/json");
        try {


            processor.process(req, resp);

        } catch (ServiceException e) {
            resp.setStatus(e.FailureStatusCode);
            resp.getWriter().println(StaticGson.toJson(e.ServiceAnswer));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(StaticGson.toJson(new Answer("Error: wrong arguments", null)));
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(StaticGson.toJson(new Answer("Error: data not found", null)));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(StaticGson.toJson(new Answer("Error: internal server error:" + e.getMessage(), null)));

        }
    }

*/
}
