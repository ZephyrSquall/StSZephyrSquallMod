package zephyrsquallmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.actions.common.StreamlineRandomCardAction;
import zephyrsquallmod.character.ZephyrSquallCharacter;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class SpireEncyclopedia extends BaseRelic {
    private static final String NAME = "SpireEncyclopedia";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;

    private static final int CARDS_TO_STREAMLINE = 1;

    public SpireEncyclopedia() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(FieldJournal.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(FieldJournal.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS_TO_STREAMLINE + DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new StreamlineRandomCardAction());
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(FieldJournal.ID);
    }
}
