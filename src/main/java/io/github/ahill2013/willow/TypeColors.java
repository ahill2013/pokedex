package io.github.ahill2013.willow;

public enum TypeColors {

    NORMAL   (0xA8A878),
    FIRE     (0xF08030),
    WATER    (0x6890F0),
    ELECTRIC (0xF8D030),
    GRASS    (0x78C850),
    ICE      (0x98D8D8),
    FIGHTING (0xC03028),
    POISON   (0xA040A0),
    GROUND   (0xE0C068),
    FLYING   (0xA890F0),
    PSYCHIC  (0xF85888),
    BUG      (0xA8B820),
    ROCK     (0xB8A038),
    GHOST    (0x705898),
    DRAGON   (0x7038F8),
    DARK     (0x705848),
    STEEL    (0xB8B8D0),
    FAIRY    (0xEE99AC);

    private final int color;

    TypeColors(int color) { this.color = color; }

    int getColor() { return color; }

}
