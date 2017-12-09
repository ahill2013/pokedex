package io.github.ahill2013.pokedex;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;

public class pokedex {

    public static void main(String[] args) {
        PokeApi pokeApi = new PokeApiClient();

        System.out.println(pokeApi.getPokemon(25).getTypes().size());
    }

}
