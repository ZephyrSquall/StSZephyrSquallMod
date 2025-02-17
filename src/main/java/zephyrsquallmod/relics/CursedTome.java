package zephyrsquallmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.character.ZephyrSquallCharacter;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class CursedTome extends BaseRelic {
    private static final String NAME = "CursedTome";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;
    public static final int DRAW_SKIP_FREQUENCY = 5;


    public CursedTome() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }
}
