package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import zephyrsquallmod.cards.skill.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class PlagiarizeAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PlagiarizeAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private int amount;

    public PlagiarizeAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        addToTop(new SelectCardsInHandAction(TEXT[0], card -> card.cardID.equals(Book.ID), plagiarizeSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> plagiarizeSelected = cards -> {
        for (int i = 0; i < this.amount; i++) {
            for (AbstractCard card : cards) {
                // Double-check the card really is a Book (this probably isn't necessary, but I never know what other
                // mods might do).
                if (card instanceof Book) {
                    // Cast the AbstractCard to a Book to use its recordedCards field.
                    Book book = (Book) card;
                    ArrayList<AbstractCard> originalRecordedCards = book.recordedCards;
                    if (originalRecordedCards == null)
                        originalRecordedCards = new ArrayList<>();
                    ArrayList<AbstractCard> copiedRecordedCards = new ArrayList<>(book.recordedCards.size());

                    for (AbstractCard originalRecordedCard : originalRecordedCards) {
                        AbstractCard copiedRecordedCard = originalRecordedCard.makeStatEquivalentCopy();
                        // Upgrade the copied recorded cards if the player has the Master Reality power (they are indeed
                        // cards created during combat!)
                        if (copiedRecordedCard.type != AbstractCard.CardType.CURSE && copiedRecordedCard.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
                            copiedRecordedCard.upgrade();
                        // There is no action for adding temporary copies of card directly to the exhaust pile. Also,
                        // all actions that add temporary copies of cards also include a visual effect of the card
                        // hovering on screen before moving to the required pile, which isn't desired here because the
                        // Book is already doing that. So the copied cards are just directly added to the exhaust pile.
                        AbstractDungeon.player.exhaustPile.addToTop(copiedRecordedCard);
                        copiedRecordedCards.add(copiedRecordedCard);
                    }

                    Book plagiarizedBook = (Book) book.makeStatEquivalentCopy();
                    plagiarizedBook.recordedCards = copiedRecordedCards;
                    addToTop(new MakeTempCardInHandAction(plagiarizedBook));
                }
            }
        }
    };
}
