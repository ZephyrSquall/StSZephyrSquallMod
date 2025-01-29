package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.cards.skill.Book;
import zephyrsquallmod.relics.ResearchPaper;

import java.util.ArrayList;

public class AppendFollowUpFollowUpAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToAppend;
    private final Book book;

    public AppendFollowUpFollowUpAction(ArrayList<AbstractCard> cardsToAppend, Book book) {
        this.cardsToAppend = cardsToAppend;
        this.book = book;
    }

    public void update() {
        book.recordedCards.addAll(cardsToAppend);
        book.updateDescription();

        for (AbstractCard card : cardsToAppend) {
            // What follows is a direct copy-paste of the resetCardBeforeMoving method on the cardGroup class. Ideally
            // I'd call this method directly, but I can't because it's private. Normally calling the method directly
            // isn't needed because public methods are provided for moving cards from hand into the draw, discard, or
            // exhaust pile, and these public methods indirectly call the resetCardBeforeMoving method. However, there
            // is no such public method for removing cards from combat entirely, as this never happens in the base game
            // (except for Bronze Orbs putting cards in stasis, which never removes cards from the player's hand).
            if (AbstractDungeon.player.hoveredCard == card)
                AbstractDungeon.player.releaseCard();
            AbstractDungeon.actionManager.removeFromQueue(card);
            card.unhover();
            card.untip();
            card.stopGlowing();
            AbstractDungeon.player.hand.group.remove(card);
            // End of resetCardBeforeMoving method copy-paste.

            if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasRelic(ResearchPaper.ID)) {
                AbstractDungeon.player.getRelic(ResearchPaper.ID).flash();
                card.upgrade();
            }
        }
        this.isDone = true;
    }
}
