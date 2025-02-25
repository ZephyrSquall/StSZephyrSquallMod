package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import zephyrsquallmod.actions.common.RecordSpecificCardsAction;
import zephyrsquallmod.cards.skill.Book;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class BrowseTheLibraryAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("BrowseTheLibraryAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private int amount;
    private CardGroup drawPileOriginalOrder;
    public BrowseTheLibraryAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        // If the draw pile has less than the required amount of cards, Record whatever is left.
        int recordableCards = AbstractDungeon.player.hand.group.size() + AbstractDungeon.player.drawPile.group.size() + AbstractDungeon.player.discardPile.group.size();
        if (recordableCards < this.amount) {
            this.amount = recordableCards;
        }

        // Sort the draw pile so its order is not revealed to the player. Remember the original order so it can be
        // restored later.
        drawPileOriginalOrder = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            drawPileOriginalOrder.addToTop(c);
        AbstractDungeon.player.drawPile.sortAlphabetically(true);
        AbstractDungeon.player.drawPile.sortByRarityPlusStatusCardType(false);

        addToTop(new MultiGroupSelectAction(TEXT[0], recordSelected, amount, false, card -> !card.cardID.equals(Book.ID), CardGroup.CardGroupType.HAND, CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE));
        this.isDone = true;
    }

    BiConsumer<List<AbstractCard>, Map<AbstractCard, CardGroup>> recordSelected = (cardsList, cardsMap) -> {
        // Return the cards in the draw pile to their original order.
        AbstractDungeon.player.drawPile.clear();
        for (AbstractCard c : drawPileOriginalOrder.group) {
            AbstractDungeon.player.drawPile.addToTop(c);
        }

        addToTop(new RecordSpecificCardsAction(cardsList, cardsMap));
    };
}
