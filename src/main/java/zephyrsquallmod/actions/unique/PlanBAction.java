package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.List;
import java.util.function.Consumer;

import static com.megacrit.cardcrawl.actions.GameActionManager.totalDiscardedThisTurn;

public class PlanBAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int drawsPerDiscard;
    private final boolean isUpgraded;
    private int initialTotalDiscardedThisTurn;

    public PlanBAction(AbstractPlayer p, int drawsPerDiscard, boolean isUpgraded) {
        this.p = p;
        this.drawsPerDiscard = drawsPerDiscard;
        this.isUpgraded = isUpgraded;
        this.initialTotalDiscardedThisTurn = totalDiscardedThisTurn;
    }

    public void update() {
        if (isUpgraded) {
            addToTop(new SelectCardsInHandAction(Integer.MAX_VALUE, TEXT[0], true, true, card -> true, discardSelected));
        } else {
            addToTop(new SelectCardsInHandAction(2, TEXT[0], true, true, card -> true, discardSelected));
        }
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> discardSelected = cards -> {
        addToTop(new PlanBFollowUpAction(p, drawsPerDiscard, initialTotalDiscardedThisTurn));
        for(AbstractCard card : cards) {
            addToTop(new DiscardSpecificCardAction(card));
        }
    };
 }
