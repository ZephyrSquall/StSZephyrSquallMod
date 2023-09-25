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

    public StreamlineAction() {}

    public void update() {
        if (AbstractDungeon.player.hand.group.stream().noneMatch(ZephyrSquallMod.canBeStreamlined))
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        else
            addToBot(new SelectCardsInHandAction(TEXT[0], ZephyrSquallMod.canBeStreamlined, streamlineSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> streamlineSelected = cards -> {
        for(AbstractCard card : cards) {
            addToBot(new StreamlineSpecificCardAction(card));
        }
    };
}
