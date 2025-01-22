package zephyrsquallmod.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.ZephyrSquallMod;

import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class StreamlineAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("StreamlineAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final int amount;
    private final boolean anyNumber;
    private final boolean canPickZero;

    public StreamlineAction(int amount, boolean anyNumber, boolean canPickZero) {
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
    }

    public StreamlineAction(int amount) {
        this(amount, false, false);
    }

    public StreamlineAction() {
        this(1, false, false);
    }

    public void update() {
        if (AbstractDungeon.player.hand.group.stream().noneMatch(ZephyrSquallMod.canBeStreamlined))
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        else
            addToTop(new SelectCardsInHandAction(amount, TEXT[0], anyNumber, canPickZero, ZephyrSquallMod.canBeStreamlined, streamlineSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> streamlineSelected = cards -> {
        for(AbstractCard card : cards) {
            addToTop(new StreamlineSpecificCardAction(card));
        }
    };
}
