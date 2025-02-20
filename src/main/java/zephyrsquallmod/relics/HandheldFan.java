package zephyrsquallmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.TailwindPower;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class HandheldFan extends BaseRelic {
    private static final String NAME = "HandheldFan";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.FLAT;

    private static final int TAILWIND_GAINED = 5;

    public HandheldFan() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TAILWIND_GAINED + DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TailwindPower(AbstractDungeon.player, TAILWIND_GAINED)));
    }
}
