package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.actions.common.RecordSpecificCardsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadAheadAction extends AbstractGameAction {
    private int amount;
    public ReadAheadAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        // If the draw pile has less than the required amount of cards, Record whatever is left.
        if (AbstractDungeon.player.drawPile.group.size() < this.amount) {
            this.amount = AbstractDungeon.player.drawPile.group.size();
        }

        ArrayList<AbstractCard> cardsToRecord = new ArrayList<>(this.amount);
        // The top card in the draw pile is the last card in AbstractDungeon.player.drawPile.group. So i needs to count
        // backwards from the final element (size() - 1).
        for (int i = AbstractDungeon.player.drawPile.group.size() - 1; i >= AbstractDungeon.player.drawPile.group.size() - this.amount; i--) {
            cardsToRecord.add(AbstractDungeon.player.drawPile.group.get(i));
        }

        Map<AbstractCard, CardGroup> cardMap = new HashMap<>();
        for (AbstractCard card : cardsToRecord) {
            cardMap.put(card, AbstractDungeon.player.drawPile);
        }
        addToTop(new RecordSpecificCardsAction(cardsToRecord, cardMap));
        this.isDone = true;
    }
}
