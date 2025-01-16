package zephyrsquallmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import zephyrsquallmod.cards.skill.Book;

import java.util.ArrayList;

public class RecordSpecificCardsAction extends AbstractGameAction {
    public final ArrayList<AbstractCard> cards;
    private final CardGroup group;

    public RecordSpecificCardsAction(ArrayList<AbstractCard> cards, CardGroup group) {
        this.cards = cards;
        this.group = group;
    }

    public void update() {
        // Make sure Books aren't recorded again by removing any books from the group.
        cards.removeIf(card -> card.cardID.equals(Book.ID));
        addToTop(new MakeTempCardInHandAction(new Book(cards), false, true));
        for (AbstractCard card : cards) {
            addToTop(new ExhaustSpecificCardAction(card, group, true));
        }
        this.isDone = true;
    }
}
