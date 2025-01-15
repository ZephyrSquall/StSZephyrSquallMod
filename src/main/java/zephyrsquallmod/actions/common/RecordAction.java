package zephyrsquallmod.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class RecordAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("RecordAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final int amount;
    private final boolean anyNumber;
    private final boolean canPickZero;

    public RecordAction(int amount, boolean anyNumber, boolean canPickZero) {
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
    }

    public void update() {
        if (AbstractDungeon.player.hand.group.isEmpty())
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        else
            addToTop(new SelectCardsInHandAction(amount, TEXT[0], anyNumber, canPickZero, card -> true, recordSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> recordSelected = cardsList -> {
        ArrayList<AbstractCard> cardsArrayList = new ArrayList<>(cardsList);
        addToTop(new RecordSpecificCardsAction(cardsArrayList, AbstractDungeon.player.hand));
    };
}
