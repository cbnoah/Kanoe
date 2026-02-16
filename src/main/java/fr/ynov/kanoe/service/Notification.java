package main.java.fr.ynov.kanoe.service;

public class Notification {
    private String titre;
    private String description;
    private String scope;
    
    public Notification(String titre, String description, String scope) {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La description ne peut pas être vide");
        }
        if (scope == null || scope.trim().isEmpty()) {
            throw new IllegalArgumentException("Le scope ne peut pas être vide");
        }

        this.titre = titre;
        this.description = description;
        this.scope = scope;
    }

        public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "[" + scope + "] " + titre + ": " + description;
    }
}
