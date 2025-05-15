/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pokemonquiz;

/**
 *  Harold Stevens
 *  Lab 8 ArrayList and HashMap
 */

public class Pokemon {

    private int number;
    private String name;
    private String type1;
    private String type2;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String imageUrl;
    private String description;

    public Pokemon( int number, String name, String type1, String type2, int hp, int attack, int defense, int speed, String imageUrl, String description ) {
        this.number = number;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
            "#%d %s\nType: %s%s\nHP: %d, Attack: %d, Defense: %d, Speed: %d\n%s",
            number, name, type1, ( type2.isEmpty() ? "" : "/" + type2 ), hp, attack, defense, speed, description
        );
    }

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    // Add other getters as needed
}
