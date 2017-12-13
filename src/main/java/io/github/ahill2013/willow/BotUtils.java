package io.github.ahill2013.willow;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.TypeRelations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

class BotUtils {

    private static final Logger logger = LogManager.getLogger();
    private static final PokeApi pokeApi = new PokeApiClient();

    static final String BOT_PREFIX = ">";

    static IDiscordClient getBuiltDiscordClient(String token){

        // The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
        // Such as withToken, setDaemon etc
        return new ClientBuilder()
                .withToken(token)
                .build();

    }

    static EmbedObject buildWeakEmbed(List<String> pokemonType) {

        logger.trace("Entered buildWeakEmbed");

        EmbedBuilder builder = new EmbedBuilder();

        Pokemon pokemon = pokeApi.getPokemon(pokemonType.get(0));
        StringBuilder type = new StringBuilder();

        if (pokemon.getTypes().size() == 2) {
            type.append(pokemon.getTypes().get(0).getType().getName());
            type.append(", ");
            type.append(pokemon.getTypes().get(1).getType().getName());
        } else {
            type.append(pokemon.getTypes().get(0).getType().getName());
        }

//        logger.debug("Type1: " + type1 + " ; Type2: " + type2);

        StringBuilder weak = new StringBuilder();
        StringBuilder resist = new StringBuilder();
        StringBuilder immune = new StringBuilder();

        if (pokemon.getTypes().size() == 1) {

            TypeRelations typeRelations = pokeApi.getType(type.toString()).getDamageRelations();

            for (int i = 0; i < typeRelations.getDoubleDamageFrom().size(); i++ ) {
                weak.append(typeRelations.getDoubleDamageFrom().get(i).getName());
                if (i < typeRelations.getDoubleDamageFrom().size() - 1)
                    weak.append(", ");
            }
            for (int i = 0; i < typeRelations.getHalfDamageFrom().size(); i++ ) {
                resist.append(typeRelations.getHalfDamageFrom().get(i).getName());
                if (i < typeRelations.getHalfDamageFrom().size() - 1)
                    resist.append(", ");
            }
            for (int i = 0; i < typeRelations.getNoDamageFrom().size(); i++ ) {
                immune.append(typeRelations.getNoDamageFrom().get(i).getName());
                if (i < typeRelations.getNoDamageFrom().size() - 1)
                    immune.append(", ");
            }

            logger.trace("Finished assigning type relations");

        } else if (pokemon.getTypes().size() == 2) {



        }

        if (weak.toString().isEmpty())
            weak.append("None");
        if (resist.toString().isEmpty())
            resist.append("None");
        if (immune.toString().isEmpty())
            immune.append("None");

        builder.withAuthorName(pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1));
        builder.withDescription("**Type:** `" + type.toString() + "`");
        builder.withThumbnail(pokemon.getSprites().getFrontDefault());

        logger.trace("Past thumbnail assignment: " + pokemon.getSprites().getFrontDefault());

        builder.appendField("Weak", weak.toString(), false);
        builder.appendField("Resist", resist.toString(), false);
        builder.appendField("Immune", immune.toString(), false);

        builder.withTimestamp(LocalDateTime.now(ZoneId.of("GMT")));

        logger.trace("Built embed");

        return builder.build();

    }

    static EmbedObject buildEmbedTest() {

        EmbedBuilder builder = new EmbedBuilder();

        builder.appendField("fieldTitleInline", "fieldContentInline", true);
        builder.appendField("fieldTitleInline2", "fieldContentInline2", true);
        builder.appendField("fieldTitleNotInline", "fieldContentNotInline", false);
        builder.appendField(":tada: fieldWithCoolThings :tada:", "[hiddenLink](http://i.imgur.com/Y9utuDe.png)", false);

        builder.withAuthorName("authorName");
        builder.withAuthorIcon("http://i.imgur.com/PB0Soqj.png");
        builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

        builder.withColor(255, 0, 0);
        builder.withDesc("withDesc");
        builder.withDescription("withDescription");
        builder.withTitle("withTitle");
        builder.withTimestamp(100);
        builder.withUrl("http://i.imgur.com/IrEVKQq.png");
        builder.withImage("http://i.imgur.com/agsp5Re.png");

        builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
        builder.withFooterText("footerText");
        builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
        builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

        builder.appendDesc(" + appendDesc");
        builder.appendDescription(" + appendDescription");

        return builder.build();
    }

    static void sendMessage(IChannel channel, String message) {

        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                logger.error("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void sendMessage(IChannel channel, EmbedObject embed) {

        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(embed);
            } catch (DiscordException e) {
                logger.error("Embed could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }
}
