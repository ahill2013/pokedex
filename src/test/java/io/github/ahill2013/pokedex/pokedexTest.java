package io.github.ahill2013.pokedex;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import org.junit.Assert;
import org.junit.Test;

public class pokedexTest {

    @Test
    public void main_test() {
        PokeApi pokeApi = new PokeApiClient();
        Assert.assertNotNull(pokeApi);

        System.out.println(pokeApi.getPokemon("lapras").getTypes());

    }


}