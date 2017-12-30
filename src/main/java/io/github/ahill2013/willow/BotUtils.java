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

import java.util.ArrayList;
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

        ArrayList<ArrayList<String>> categories = new ArrayList<>();


        //Pokemon pokemon = null;
        ArrayList<String> type = null;

        try {

            logger.debug("Attempting getPokemon query");
            Pokemon pokemon = pokeApi.getPokemon(pokemonType.get(0));
            logger.debug("getPokemon API request received");

            type = new ArrayList<>(2);

            if (pokemon.getTypes().size() == 2) {
                type.add(pokemon.getTypes().get(0).getType().getName());
                type.add(pokemon.getTypes().get(1).getType().getName());
            } else {
                type.add(pokemon.getTypes().get(0).getType().getName());
            }

            if (type.size() == 1) {

                logger.trace("Pokemon has 1 type");

                categories = calculate1Type(type);

            } else if (type.size() == 2) {

                logger.trace("Pokemon has 2 types");

                categories = calculate2Types(type);

            }

            logger.trace("Finished assigning type relations");

            builder.withAuthorName(pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1));
            builder.withDescription("**Type:** `" + type.toString() + "`");
            builder.withColor(findColor(type.get(0)));
            builder.withThumbnail(pokemon.getSprites().getFrontDefault());

            logger.trace("Past thumbnail assignment: " + pokemon.getSprites().getFrontDefault());

        } catch (Throwable t) {

            logger.error(t.getMessage());
            //logger.debug("Attempting getType query");



        }

        ArrayList<String> weak = categories.get(0);
        ArrayList<String> resist = categories.get(1);
        ArrayList<String> immune = categories.get(2);

        if (weak.isEmpty()) weak.add("None");
        if (resist.isEmpty()) resist.add("None");
        if (immune.isEmpty()) immune.add("None");

        builder.appendField("Weak", weak.toString(), false);
        builder.appendField("Resist", resist.toString(), false);
        builder.appendField("Immune", immune.toString(), false);

        builder.withFooterText("* Types not listed are Neutral *");
//        builder.withTimestamp(LocalDateTime.now(ZoneId.of("GMT")));

        logger.trace("Built embed");

        return builder.build();

    }

    private static ArrayList<ArrayList<String>> calculate1Type(ArrayList<String> type) {
        TypeRelations typeRelations = pokeApi.getType(type.get(0)).getDamageRelations();
        logger.debug("getType API request received");

        ArrayList<String> weak = new ArrayList<>();
        ArrayList<String> resist = new ArrayList<>();
        ArrayList<String> immune = new ArrayList<>();

        for (int i = 0; i < typeRelations.getDoubleDamageFrom().size(); i++ )
            weak.add(typeRelations.getDoubleDamageFrom().get(i).getName());
        for (int i = 0; i < typeRelations.getHalfDamageFrom().size(); i++ )
            resist.add(typeRelations.getHalfDamageFrom().get(i).getName());
        for (int i = 0; i < typeRelations.getNoDamageFrom().size(); i++ )
            immune.add(typeRelations.getNoDamageFrom().get(i).getName());

        ArrayList<ArrayList<String>> categories = new ArrayList<>();
        categories.add(weak);
        categories.add(resist);
        categories.add(immune);

        return categories;
    }

    private static ArrayList<ArrayList<String>> calculate2Types(ArrayList<String> type) {
        TypeRelations type1Relations = pokeApi.getType(type.get(0)).getDamageRelations();
        logger.trace("getType API received for type1");
        TypeRelations type2Relations = pokeApi.getType(type.get(1)).getDamageRelations();
        logger.trace("getType API received for type2");

        ArrayList<String> weak = new ArrayList<>();
        ArrayList<String> resist = new ArrayList<>();
        ArrayList<String> immune = new ArrayList<>();

        ArrayList<String> weakTemp = new ArrayList<>();
        ArrayList<String> resistTemp = new ArrayList<>();

        for (int i = 0; i < type1Relations.getDoubleDamageFrom().size(); i++)
            weakTemp.add(type1Relations.getDoubleDamageFrom().get(i).getName());
        for (int i = 0; i < type1Relations.getHalfDamageFrom().size(); i++)
            resistTemp.add(type1Relations.getHalfDamageFrom().get(i).getName());
        // Add immune types
        for (int i = 0; i < type1Relations.getNoDamageFrom().size(); i++)
            immune.add(type1Relations.getNoDamageFrom().get(i).getName());

        // Add types with 1/4x weakness in bold
        for (int i = 0; i < type2Relations.getDoubleDamageFrom().size(); i++) {
            String typeName = type2Relations.getDoubleDamageFrom().get(i).getName();
            findDoubleRelation(weak, immune, weakTemp, resistTemp, typeName);
        }
        // Add types with 4x resistance in bold
        for (int i = 0; i < type2Relations.getHalfDamageFrom().size(); i++) {
            String typeName = type2Relations.getHalfDamageFrom().get(i).getName();
            findDoubleRelation(resist, immune, resistTemp, weakTemp, typeName);
        }
        // Finish adding immune types
        for (int i = 0; i < type2Relations.getNoDamageFrom().size(); i++) {
            String typeName = type2Relations.getNoDamageFrom().get(i).getName();
            if (!immune.contains(typeName)) {
                immune.add(typeName);
            }
        }
        // Add 1/2x weak types
        weak.addAll(weakTemp);
        // Add 2x resist types
        resist.addAll(resistTemp);

        ArrayList<ArrayList<String>> categories = new ArrayList<>();
        categories.add(weak);
        categories.add(resist);
        categories.add(immune);

        return categories;
    }

    private static int findColor(String type) {
        return TypeColors.valueOf(type.toUpperCase()).getColor();
    }

    private static void findDoubleRelation(ArrayList<String> weak, ArrayList<String> immune, ArrayList<String> weakTemp, ArrayList<String> resistTemp, String typeName) {
        if (immune.contains(typeName)) {
            return;
        } else if (weakTemp.contains(typeName)) {
            weak.add("**" + typeName + "**");
            weakTemp.remove(typeName);
        } else if (resistTemp.contains(typeName)) {
            resistTemp.remove(typeName);
        } else {
            weakTemp.add(typeName);
        }
    }

    static EmbedObject buildHelpEmbed() {

        EmbedBuilder builder = new EmbedBuilder();

        builder.withAuthorIcon(CommandHandler._client.getOurUser().getAvatarURL());
        builder.withAuthorName("Professor Willow Help");
        builder.withDescription("Bot command prefix is: `>`");

        builder.appendField("Commands", "`weak <pokémon>`\n`ping`\n`help`", true);
        builder.appendField("Donate", "If you would like to help support server and development costs, or just want to say thanks, feel free to donate via [PayPal](https://paypal.me/pools/c/807lULTr0e)", true);

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
