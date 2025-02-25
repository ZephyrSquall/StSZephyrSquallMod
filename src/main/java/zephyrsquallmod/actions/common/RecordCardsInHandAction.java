package zephyrsquallmod.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.cards.skill.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class RecordCardsInHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("RecordAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private final int amount;
    private final boolean anyNumber;
    private final boolean canPickZero;

    public RecordCardsInHandAction(int amount, boolean anyNumber, boolean canPickZero) {
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
    }

    public void update() {
        int recordableCards = AbstractDungeon.player.hand.size();
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.cardID.equals(Book.ID)) {
                recordableCards--;
            }
        }
        if (recordableCards == 0)
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        else
            addToTop(new SelectCardsInHandAction(amount, TEXT[0], anyNumber, canPickZero, card -> !card.cardID.equals(Book.ID), recordSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> recordSelected = cardList -> {
        Map<AbstractCard, CardGroup> cardMap = new HashMap<>();
        for (AbstractCard card : cardList) {
            cardMap.put(card, AbstractDungeon.player.hand);
        }
        addToTop(new RecordSpecificCardsAction(cardList, cardMap));
    };
}
