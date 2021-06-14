package org.practical3.logic;

import org.practical3.model.transfer.Answer;
import org.practical3.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.practical3.utils.StaticGson.toJson;

public class ExceptionHandler {
    public interface Executor {
        void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }

    public static  void execute(HttpServletRequest req, HttpServletResponse resp, Executor executor) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            executor.execute(req, resp);



        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(toJson(new Answer("Wrong arguments",null)));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(toJson(new Answer("Failed to connect to DataBase",null)));
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(toJson(new Answer("No data found ",null)));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(toJson(new Answer("Internal server error - " + e.getMessage(),null)));
        }
        finally {
            writer.close();
        }
    }
}
