package zephyrsquallmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.cards.skill.Book;

import java.util.ArrayList;

public class RecordSpecificCardsAction extends AbstractGameAction {
    public final ArrayList<AbstractCard> cards;
    private final CardGroup cardGroup;

    public RecordSpecificCardsAction(ArrayList<AbstractCard> cards, CardGroup cardGroup) {
        this.cards = cards;
        this.cardGroup = cardGroup;
    }

    public void update() {
        // Make sure Books aren't recorded again by removing any books from the group.
        cards.removeIf(card -> card.cardID.equals(Book.ID));
        addToTop(new MakeTempCardInHandAction(new Book(cards), false, true));
        for (AbstractCard card : cards) {
            // What follows is a direct copy-paste of the resetCardBeforeMoving method on the cardGroup class. Ideally
            // I'd call this method directly, but I can't because it's private. Normally calling the method directly
            // isn't needed because public methods are provided for moving cards from hand into the draw, discard, or
            // exhaust pile, and these public methods indirectly call the resetCardBeforeMoving method. However, there
            // is no such public method for removing cards from combat entirely, as this never happens in the base game
            // except for Bronze Orbs putting cards in stasis, which never remove cards from the player's hand.
            if (AbstractDungeon.player.hoveredCard == card)
                AbstractDungeon.player.releaseCard();
            AbstractDungeon.actionManager.removeFromQueue(card);
            card.unhover();
            card.untip();
            card.stopGlowing();
            this.cardGroup.group.remove(card);
            // End of resetCardBeforeMoving method copy-paste.
        }
        this.isDone = true;
    }
}
