package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class BookAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> recordedCards;

    public BookAction(ArrayList<AbstractCard> recordedCards) {
        this.recordedCards = recordedCards;
    }

    public void update() {
        // Process the cards in reverse order, so they appear in hand in the same order that they appear in the recorded
        // cards preview.
        for (int i = recordedCards.size() - 1; i >= 0; i--)
            addToTop(new MakeTempCardInHandAction(recordedCards.get(i), false, true));
        this.isDone = true;
    }
}
