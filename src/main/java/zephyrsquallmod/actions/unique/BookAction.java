package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BookAction extends AbstractGameAction {
    private ArrayList<AbstractCard> recordedCards;

    public BookAction(ArrayList<AbstractCard> recordedCards) {
        this.recordedCards = recordedCards;
    }

    private final Predicate<AbstractCard> isRecordedCard = card -> this.recordedCards.contains(card);

    public void update() {
        addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, isRecordedCard, Integer.MAX_VALUE));
        this.isDone = true;
    }
}
