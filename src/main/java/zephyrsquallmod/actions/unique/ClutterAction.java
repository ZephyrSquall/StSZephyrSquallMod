package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.attack.Clutter;

public class ClutterAction extends AbstractGameAction {
    private final AbstractCard card;
    private final boolean upgraded;

    public ClutterAction(AbstractCard card, boolean upgraded) {
        this.card = card;
        this.upgraded = upgraded;
    }

    public void update() {
        int clutterLimit = upgraded ? Clutter.UPG_CLUTTER_LIMIT : Clutter.CLUTTER_LIMIT;
        if (ZephyrSquallMod.cluttersPlayedThisTurn < clutterLimit) {
            addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
        }

        // The number of Clutters played this turn isn't incremented until after the check to see if a new Clutter
        // should be created, making this Clutter that's in the middle of being played not count towards the total. This
        // makes Clutter consistent with the basegame card FTL, which doesn't count itself as the third (fourth if
        // upgraded) card played that turn.
        ZephyrSquallMod.cluttersPlayedThisTurn += 1;
        this.isDone = true;
    }
}