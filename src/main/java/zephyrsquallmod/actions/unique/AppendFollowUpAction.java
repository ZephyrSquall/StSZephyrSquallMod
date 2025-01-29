package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import zephyrsquallmod.actions.common.RecordSpecificCardsAction;
import zephyrsquallmod.cards.skill.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class AppendFollowUpAction extends AbstractGameAction {
    private static final UIStrings appendUiStrings = CardCrawlGame.languagePack.getUIString(makeID("AppendAction"));
    public static final String[] APPEND_TEXT = appendUiStrings.TEXT;
    private ArrayList<AbstractCard> cardsToAppend;

    public AppendFollowUpAction(ArrayList<AbstractCard> cardsToAppend) {
        this.cardsToAppend = cardsToAppend;
    }

    public void update() {
        // If the player's hand has at least one Book.
        if (AbstractDungeon.player.hand.group.stream().anyMatch(card -> card.cardID.equals(Book.ID))) {
            addToTop(new SelectCardsInHandAction(APPEND_TEXT[0], card -> card.cardID.equals(Book.ID), appendToBook));
        } else {
            addToTop(new RecordSpecificCardsAction(cardsToAppend, AbstractDungeon.player.hand));
        }
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> appendToBook = bookList -> {
        Book book = (Book) bookList.get(0);
        // A second follow-up action is needed, because all non-Books were filtered out by the SelectCardsInHandAction's
        // predicate, but those non-Books need to be returned to hand first because the cards to be appended are among
        // them.
        addToTop(new AppendFollowUpFollowUpAction(cardsToAppend, book));
    };
}
