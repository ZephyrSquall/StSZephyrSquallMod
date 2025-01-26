package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;

import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class RefineAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("RefineAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final int amount;

    public RefineAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        addToTop(new SelectCardsInHandAction(amount, TEXT[0], card -> (card.canUpgrade() || ZephyrSquallMod.canBeStreamlined.test(card)), refineSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> refineSelected = cards -> {
        for(AbstractCard card : cards) {
            if (card.canUpgrade()) {
                card.upgrade();
                card.superFlash();
                card.applyPowers();
            }
            addToTop(new StreamlineSpecificCardAction(card));
        }
    };
}
