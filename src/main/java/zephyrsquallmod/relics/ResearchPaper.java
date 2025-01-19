package zephyrsquallmod.relics;

import zephyrsquallmod.character.ZephyrSquallCharacter;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class ResearchPaper extends BaseRelic {
    private static final String NAME = "ResearchPaper";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.FLAT;

    public ResearchPaper() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
