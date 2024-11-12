package com.petstore;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PetStoreAPI api = new PetStoreAPI();
        String username = "testUser";

        try {
            // Create a new user
            api.createUser(username);

            // Retrieve user data
            JsonNode userData = api.getUser(username);
            System.out.println("User Data: " + userData);

            // Find pets by status
            List<Map<String, Object>> soldPets = api.getPetsByStatus("sold");
            System.out.println("Sold Pets: ");
            soldPets.forEach(pet -> System.out.println(pet));

            // Count pet names
            PetNameCounter counter = new PetNameCounter(soldPets);
            Map<String, Integer> nameCounts = counter.getNameCounts();
            System.out.println("Pet Name Counts: " + nameCounts);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
