package org.megaline.database.manager.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import org.megaline.database.manager.UserManager;
import org.megaline.database.manager.models.User;
import org.megaline.httpserver.http.HttpRequest;
import org.megaline.httpserver.http.HttpResponse;
import org.megaline.httpserver.http.HttpStatusCode;
import org.megaline.httpserver.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserProcessingThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProcessingThread.class);
    private final HttpRequest request;
    private final HttpResponse response;
    private final UserManager userManager;

    public UserProcessingThread(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
        this.userManager = new UserManager();
    }

    @Override
    public void run() {
        try {
            switch (request.getMethod()) {
                case GET:
                    handleGet();
                    break;
                case POST:
                    handlePost();
                    break;
                case DELETE:
                    handleDelete();
                    break;
                case PUT:
                    handlePut();
                    break;
                default:
                    response.setStatusCode(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    response.setBody(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED.MESSAGE);
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Error processing request", e);
            response.setStatusCode(HttpStatusCode.SERVER_ERROR_500_INTERNAL_ERROR);
            response.setBody(HttpStatusCode.SERVER_ERROR_500_INTERNAL_ERROR.MESSAGE);
        }
    }

    private void handleGet() throws SQLException, IOException {
        UUID userId = extractUserIdFromRequest();
        if (userId == null) {
            response.setStatusCode(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            response.setBody("Bad Request: Missing userId");
            return;
        }

        User user = userManager.getUserById(userId);
        if (user == null) {
            response.setStatusCode(HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND);
            response.setBody("User Not Found");
        } else {
            response.setStatusCode(HttpStatusCode.SERVER_CONNECTED_200);
            response.setBody(Json.stringify(Json.toJson(user)));
        }
    }

    private void handlePost() throws IOException, SQLException {
        JsonNode jsonNode = Json.parse(request.getBody());
        String userName = jsonNode.get("userName").asText();
        String userAddress = jsonNode.get("userAddress").asText();
        String userPassportId = jsonNode.get("userPassportId").asText();

        User user = new User(userName, userAddress, userPassportId);
        userManager.create(user);

        response.setStatusCode(HttpStatusCode.SERVER_CONNECTED_201_CREATED);
        response.setBody("User " + HttpStatusCode.SERVER_CONNECTED_201_CREATED.MESSAGE);
    }

    private void handleDelete() throws IOException, SQLException {
        UUID userId = extractUserIdFromRequest();
        if (userId == null) {
            response.setStatusCode(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            response.setBody(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE + " : Missing userId");
            return;
        }

        User user = new User(userId, null, null, null);
        userManager.delete(user);

        response.setStatusCode(HttpStatusCode.SERVER_CONNECTED_200);
        response.setBody("User Deleted Successfully");
    }

    private void handlePut() throws IOException, SQLException {
        JsonNode jsonNode = Json.parse(request.getBody());
        UUID userId = UUID.fromString(jsonNode.get("userId").asText());
        String userName = jsonNode.get("userName").asText();
        String userAddress = jsonNode.get("userAddress").asText();
        String userPassportId = jsonNode.get("userPassportId").asText();

        User user = new User(userId, userName, userAddress, userPassportId);
        userManager.update(user, "userName", userName, "userAddress", userAddress, "userPassportId", userPassportId);

        response.setStatusCode(HttpStatusCode.SERVER_CONNECTED_200);
        response.setBody("User Updated Successfully");
    }

    private UUID extractUserIdFromRequest() throws IOException {
        JsonNode jsonNode = Json.parse(request.getBody());
        return UUID.fromString(jsonNode.get("userId").asText());
    }
}
