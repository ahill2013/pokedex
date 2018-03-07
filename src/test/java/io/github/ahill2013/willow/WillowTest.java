package io.github.ahill2013.willow;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import org.junit.Assert;
import org.junit.Test;

public class WillowTest {

    @Test
    public void main_test() {
        PokeApi pokeApi = new PokeApiClient();
        Assert.assertNotNull(pokeApi);

        System.out.println(pokeApi.getPokemon("mew"));

    }



}