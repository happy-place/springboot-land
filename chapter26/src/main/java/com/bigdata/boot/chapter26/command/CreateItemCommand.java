package com.bigdata.boot.chapter26.command;

import com.bigdata.boot.chapter26.model.Item;

public class CreateItemCommand {
    private String description;
    private Item.Priority priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item.Priority getPriority() {
        return priority;
    }

    public void setPriority(Item.Priority priority) {
        this.priority = priority;
    }
}
