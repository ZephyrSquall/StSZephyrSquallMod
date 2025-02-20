package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;

import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class FieldJournalAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("FieldJournalAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final int amount;
    private final boolean anyNumber;

    public FieldJournalAction(int amount, boolean anyNumber) {
        this.amount = amount;
        this.anyNumber = anyNumber;
    }

    public FieldJournalAction() {
        this(1, false);
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.group.stream().noneMatch(ZephyrSquallMod.canBeStreamlined))
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        else
            addToTop(new SelectCardsAction(AbstractDungeon.player.drawPile.group, amount, TEXT[0], anyNumber, ZephyrSquallMod.canBeStreamlined, streamlineSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> streamlineSelected = cards -> {
        for(AbstractCard card : cards) {
            addToTop(new StreamlineSpecificCardAction(card));
        }
    };
}
