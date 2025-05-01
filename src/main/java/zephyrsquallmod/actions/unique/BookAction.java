package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import zephyrsquallmod.powers.ShhPower;

import java.util.ArrayList;

public class BookAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> recordedCards;

    public BookAction(ArrayList<AbstractCard> recordedCards) {
        this.recordedCards = recordedCards;
    }

    public void update() {
        int shhBlock = 0;
        if (AbstractDungeon.player.hasPower(ShhPower.POWER_ID)) {
            AbstractPower power = AbstractDungeon.player.getPower(ShhPower.POWER_ID);
            shhBlock = power.amount;
            power.flash();
        }

        // Process the cards in reverse order, so they appear in hand in the same order that they appear in the recorded
        // cards preview.
        for (int i = recordedCards.size() - 1; i >= 0; i--) {
            if (shhBlock > 0) {
                addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, shhBlock));
            }
            addToTop(new MakeTempCardInHandAction(recordedCards.get(i), false, true));
        }
        this.isDone = true;
    }
}
