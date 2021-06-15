package com.github.michael_sharko.handlers;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.utils.StaticGson;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;

public class ExceptionHandler {
    public static void execute(HttpServletRequest request, HttpServletResponse response, Executor executor) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            Answer answer = executor.execute(request, response);
            writer.write(StaticGson.toJson(answer));
        } catch (InvalidParameterException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            writer.write(StaticGson.toJson(new Answer(e.getMessage(), null)));
        } catch (Exception e) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            writer.write(StaticGson.toJson(new Answer(e.getMessage(), null)));
        } finally {
            writer.close();
        }
    }

    public interface Executor {
        Answer execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }
}
