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
    public interface Executor<T> {
        T execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }

    public static <T> void execute(HttpServletRequest request, HttpServletResponse response, Executor<T> executor) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            Object result = executor.execute(request, response);
            writer.write(StaticGson.toJson(new Answer("Success", result)));
        } catch (InvalidParameterException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            writer.write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
