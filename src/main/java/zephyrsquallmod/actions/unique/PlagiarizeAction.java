package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import zephyrsquallmod.cards.skill.Book;

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
        addToTop(new SelectCardsInHandAction(TEXT[0], card -> card.cardID.equals(Book.ID), copySelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> copySelected = cards -> {
        for (int i = 0; i < this.amount; i++)
            for (AbstractCard card : cards)
                    addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
    };
}
