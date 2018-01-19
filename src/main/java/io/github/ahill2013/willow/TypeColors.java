package io.github.ahill2013.willow;

public enum TypeColors {

    NORMAL   (0xA8A878, "<:normalType:396584367968878592>",   "https://pre00.deviantart.net/d0a9/th/pre/i/2014/231/d/f/normal_medallion_by_zekrom_9-d7vrykq.png"),
    FIRE     (0xF08030, "<:fireType:396584363288166400>",     "https://pre00.deviantart.net/751b/th/pre/i/2014/242/3/8/fire_medallion_by_zekrom_9-d7x8ho3.png"),
    WATER    (0x6890F0, "<:waterType:396584364730875904>",    "https://pre00.deviantart.net/9c4d/th/pre/i/2014/242/b/1/water_medallion_by_zekrom_9-d7x8hp0.png"),
    ELECTRIC (0xF8D030, "<:electricType:396584252755410955>", "https://img00.deviantart.net/16ab/i/2014/242/6/8/electric_medallion_by_zekrom_9-d7x8hpr.png"),
    GRASS    (0x78C850, "<:grassType:396584376101765120>",    "https://pre00.deviantart.net/3477/th/pre/i/2014/242/f/a/grass_medallion_by_zekrom_9-d7x8iod.png"),
    ICE      (0x98D8D8, "<:iceType:396584381935779850>",      "https://pre00.deviantart.net/da3c/th/pre/i/2014/248/1/9/ice_medallion_by_zekrom_9-d7xnywf.png"),
    FIGHTING (0xC03028, "<:fightingType:396584166726041602>", "https://pre00.deviantart.net/55be/th/pre/i/2014/230/c/1/fighting_medallion_by_zekrom_9-d7vp55x.png"),
    POISON   (0xA040A0, "<:poisonType:396584372402388997>",   "https://pre00.deviantart.net/6a08/th/pre/i/2014/248/7/f/poison_medallion_by_zekrom_9-d7y06yw.png"),
    GROUND   (0xE0C068, "<:groundType:396584364009324546>",   "https://pre00.deviantart.net/6ef9/th/pre/i/2014/260/e/5/ground_medallion_by_zekrom_9-d7zhheu.png"),
    FLYING   (0xA890F0, "<:flyingType:396584377993396227>",   "https://img00.deviantart.net/0ded/i/2015/014/4/9/flying_medallion_by_zekrom_9-d8dwkq0.png"),
    PSYCHIC  (0xF85888, "<:psychicType:396584381793304586>",  "https://pre00.deviantart.net/3254/th/pre/i/2014/230/f/a/psychic_medallion_by_zekrom_9-d7voplx.png"),
    BUG      (0xA8B820, "<:bugType:396584370745376771>",      "https://orig00.deviantart.net/84b1/f/2014/278/6/2/62319a6960cd7aeb4a54652a905b5c46-d81nyti.png"),
    ROCK     (0xB8A038, "<:rockType:396584343167959040>",     "https://pre00.deviantart.net/e5d6/th/pre/i/2014/269/4/c/rock_medallion_by_zekrom_9-d80jvnn.png"),
    GHOST    (0x705898, "<:ghostType:396584363699208194>",    "https://pre00.deviantart.net/894f/th/pre/i/2014/311/1/f/ghost_medallion_by_zekrom_9-d85lmun.png"),
    DRAGON   (0x7038F8, "<:dragonType:396584375317168128>",   "https://pre00.deviantart.net/75e1/th/pre/i/2014/241/1/6/dragon_medallion_by_zekrom_9-d7x3gtw.png"),
    DARK     (0x705848, "<:darkType:396584306748948480>",     "https://pre00.deviantart.net/7150/th/pre/i/2014/232/8/8/dark_medallion_by_zekrom_9-d7w1aid.png"),
    STEEL    (0xB8B8D0, "<:steelType:396583760352772126>",    "https://img00.deviantart.net/0669/i/2014/234/c/7/steel_medallion_by_zekrom_9-d7w7cxy.png"),
    FAIRY    (0xEE99AC, "<:fairyType:396584344912789505>",    "https://pre00.deviantart.net/cbf1/th/pre/i/2014/241/e/f/fairy_medallion_by_zekrom_9-d7x4b2e.png");

    private final int color;
    private final String emoji;
    private final String url;

    TypeColors(int color, String emoji, String url) {
        this.color = color;
        this.emoji = emoji;
        this.url = url;
    }

    int getColor() { return color; }

    String getEmoji() { return emoji; }

    String getUrl() { return url; }

}
