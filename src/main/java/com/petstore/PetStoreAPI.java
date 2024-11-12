package com.petstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetStoreAPI {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to create a user
    public void createUser(String username) throws IOException, InterruptedException {
        String requestBody = String.format("""
                {
                  "id": 0,
                  "username": "%s",
                  "firstName": "John",
                  "lastName": "Doe",
                  "email": "john.doe@example.com",
                  "password": "password123",
                  "phone": "1234567890",
                  "userStatus": 1
                }
                """, username);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create User Response: " + response.statusCode() + " - " + response.body());
    }

    // Method to retrieve user data
    public JsonNode getUser(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/user/" + username))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }

    // Method to find pets by status
    public List<Map<String, Object>> getPetsByStatus(String status) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/findByStatus?status=" + status))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode petsArray = objectMapper.readTree(response.body());

        List<Map<String, Object>> petsList = new ArrayList<>();
        for (JsonNode pet : petsArray) {
            int id = pet.get("id").asInt();
            String name = pet.hasNonNull("name") ? pet.get("name").asText() : "Unnamed";
            Map<String, Object> petInfo = new HashMap<>();
            petInfo.put("id", id);
            petInfo.put("name", name);
            petsList.add(petInfo);
        }

        return petsList;
    }
}
