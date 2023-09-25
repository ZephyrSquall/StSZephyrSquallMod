package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;

import java.util.ArrayList;

public class FlashOfInspirationAction extends AbstractGameAction {
    private final int cardsToStreamline;

    public FlashOfInspirationAction(int cardsToStreamline) {
        this.cardsToStreamline = cardsToStreamline;
    }

    public void update() {
        ArrayList<AbstractCard> groupCopy = new ArrayList<>();
        for (AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
            if (ZephyrSquallMod.canBeStreamlined.test(abstractCard))
                groupCopy.add(abstractCard);
        }
        for(int i = 0; i < cardsToStreamline; i++) {
            if (groupCopy.isEmpty()) {
                this.isDone = true;
                return;
            } else {
                AbstractCard card = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                addToBot(new StreamlineSpecificCardAction(card));
                groupCopy.remove(card);
            }
        }
        this.isDone = true;
    }
}
