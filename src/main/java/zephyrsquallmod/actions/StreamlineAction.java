package zephyrsquallmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class StreamlineAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("StreamlineAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final float startingDuration;

    public StreamlineAction(AbstractCreature target, AbstractCreature source) {
        setValues(target, source);
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    Predicate<AbstractCard> canBeStreamlined = card -> card.cost > 0 && card.costForTurn > 0 && !card.freeToPlayOnce;
    Consumer<List<AbstractCard>> streamlineSelected = cards -> {
        for(AbstractCard card : cards) {
            addToBot(new StreamlineSpecificCardAction(card));
        }
    };

    public void update() {
        if (this.duration == this.startingDuration) {
            addToBot(new SelectCardsInHandAction(TEXT[0], canBeStreamlined, streamlineSelected));
            this.isDone = true;
        }
    }
}