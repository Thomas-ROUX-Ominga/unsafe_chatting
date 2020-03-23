package fr.ominga.mayday_firebase;

public class Users {
    public String name;
    public String status;
    public String pokemon;
    public String pokemon_url;

    public Users(){

    }
    public Users(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public String getPokemon_url() {
        return pokemon_url;
    }

    public void setPokemon_url(String pokemon_url) {
        this.pokemon_url = pokemon_url;
    }
}
