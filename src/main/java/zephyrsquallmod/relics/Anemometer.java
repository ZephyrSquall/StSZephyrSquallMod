package zephyrsquallmod.relics;

import zephyrsquallmod.character.ZephyrSquallCharacter;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class Anemometer extends BaseRelic {
    private static final String NAME = "Anemometer";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.FLAT;

    public Anemometer() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
