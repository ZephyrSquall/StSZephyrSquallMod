package zephyrsquallmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.cards.skill.Book;
import zephyrsquallmod.relics.ResearchPaper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordSpecificCardsAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardList;
    private final Map<AbstractCard, CardGroup> cardMap;

    public RecordSpecificCardsAction(List<AbstractCard> cardList, Map<AbstractCard, CardGroup> cardMap) {
        this.cardList = new ArrayList<>(cardList);
        this.cardMap = cardMap;
    }

    public void update() {
        // Don't create a Book if 0 cards were provided to be Recorded.
        if (!cardList.isEmpty()) {
            // Make sure Books aren't recorded again by removing any books from the group.
            cardList.removeIf(card -> card.cardID.equals(Book.ID));
            addToTop(new MakeTempCardInHandAction(new Book(cardList), false, true));
            for (AbstractCard card : cardList) {
                CardGroup cardGroup = cardMap.get(card);
                // What follows is a direct copy-paste of the resetCardBeforeMoving method on the cardGroup class.
                // Ideally I'd call this method directly, but I can't because it's private. Normally calling the method
                // directly isn't needed because public methods are provided for moving cards from hand into the draw,
                // discard, or exhaust pile, and these public methods indirectly call the resetCardBeforeMoving method.
                // However, there is no such public method for removing cards from combat entirely, as this never
                // happens in the base game (except for Bronze Orbs putting cards in stasis, which never removes cards
                // from the player's hand).
                if (AbstractDungeon.player.hoveredCard == card)
                    AbstractDungeon.player.releaseCard();
                AbstractDungeon.actionManager.removeFromQueue(card);
                card.unhover();
                card.untip();
                card.stopGlowing();
                cardGroup.group.remove(card);
                // End of resetCardBeforeMoving method copy-paste.

                if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasRelic(ResearchPaper.ID)) {
                    AbstractDungeon.player.getRelic(ResearchPaper.ID).flash();
                    card.upgrade();
                }
            }
        }
        this.isDone = true;
    }
}
