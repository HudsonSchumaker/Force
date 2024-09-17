package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_POST;

/**
 * The PostHandler class.
 * This class is responsible for processing HTTP_POST requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PostHandler extends AbstractRequestHandler {

    /**
     * Process the HTTP_POST request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        return processRequest(request, HTTP_POST, Post.class);
    }
}
