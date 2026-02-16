package main.java.fr.ynov.kanoe.model;
public class Users {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private int id;
    
    public Users(String nom, String prenom, String email, String telephone, String motDePasse, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.id = id;
    }

    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getId() {
        return id;
    }
    
    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }
    
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
