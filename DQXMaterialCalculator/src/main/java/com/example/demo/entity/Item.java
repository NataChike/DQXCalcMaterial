package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;



public class Item {
    private String name;
    private String job;
    private List<Material> materials = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public List<Material> getMaterials() { return materials; }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
