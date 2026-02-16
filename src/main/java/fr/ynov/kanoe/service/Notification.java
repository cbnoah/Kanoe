package main.java.fr.ynov.kanoe.service;

public class Notification {
    private String title;
    private String description;
    private String scope;
    
    public Notification(String title, String description, String scope) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("The title cannot be empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("The description cannot be empty");
        }
        if (scope == null || scope.trim().isEmpty()) {
            throw new IllegalArgumentException("The scope cannot be empty");
        }

        this.title = title;
        this.description = description;
        this.scope = scope;
    }

        public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "[" + scope + "] " + title + ": " + description;
    }
}
