package zephyrsquallmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.character.ZephyrSquallCharacter;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class BrokenSpectacles extends BaseRelic {
    private static final String NAME = "BrokenSpectacles";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.FLAT;
    private static final int BLOCK_PER_CARD = 1;

    public BrokenSpectacles() {
        super(ID, NAME, ZephyrSquallCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_PER_CARD + DESCRIPTIONS[1] ;
    }

    @Override
    public void onPlayerEndTurn() {
        int cardsInHand = AbstractDungeon.player.hand.size();
        if (cardsInHand > 0) {
            flash();
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, cardsInHand));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
}
