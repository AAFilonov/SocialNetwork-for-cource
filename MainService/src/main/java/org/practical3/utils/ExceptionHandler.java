package org.practical3.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.http.ResponseReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.function.Function;

public class ExceptionHandler {
    public interface Executor {
        void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }

    public static  void execute(HttpServletRequest req, HttpServletResponse resp, Executor executor) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            executor.execute(req, resp);

        } catch (ServiceException e) {
            resp.setStatus(e.FailureStatusCode);
            writer.println(StaticGson.toJson(e.ServiceAnswer));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println(StaticGson.toJson(new Answer("Error: wrong arguments", null)));
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.println(StaticGson.toJson(new Answer("Error: data not found", null)));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.println(StaticGson.toJson(new Answer("Error: internal server error:" + e.getMessage(), null)));

        }
        finally {
            writer.close();
        }
    }


    public static   <T> T checkResponse(HttpResponse response, Function<HttpResponse,T> returnIfOk) throws IOException, ServiceException {

        if( response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK)
            return returnIfOk.apply(response);
        else {
            pullExceptionByResponseCode(response);
            return null;
        }
    }
    private static void pullExceptionByResponseCode(HttpResponse response) throws IOException, ServiceException {
        Answer postServiceAnswer = ResponseReader.getAnswer(response);
        System.out.println("[SERVICE ERROR]: "+ postServiceAnswer.Status);

        throw  new ServiceException(postServiceAnswer,
                response.getStatusLine().getStatusCode());
    }

}
