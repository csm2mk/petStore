package com.petstore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetNameCounter {
    private final Map<String, Integer> nameCountMap;

    public PetNameCounter(List<Map<String, Object>> pets) {
        this.nameCountMap = new HashMap<>();
        countPetNames(pets);
    }

    private void countPetNames(List<Map<String, Object>> pets) {
        for (Map<String, Object> pet : pets) {
            String name = (String) pet.get("name");
            nameCountMap.put(name, nameCountMap.getOrDefault(name, 0) + 1);
        }
    }

    public Map<String, Integer> getNameCounts() {
        return nameCountMap;
    }
}
