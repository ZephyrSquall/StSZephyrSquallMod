package zephyrsquallmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class FlashOfInspirationAction extends AbstractGameAction {
    private final int cardsToStreamline;

    public FlashOfInspirationAction(AbstractCreature target, AbstractCreature source, int cardsToStreamline) {
        setValues(target, source);
        this.cardsToStreamline = cardsToStreamline;
    }

    public void update() {
        ArrayList<AbstractCard> groupCopy = new ArrayList<>();
        for (AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
            if (abstractCard.cost > 0 && abstractCard.costForTurn > 0 && !abstractCard.freeToPlayOnce)
                groupCopy.add(abstractCard);
        }
        for(int i = 0; i < cardsToStreamline; i++) {
            if (groupCopy.isEmpty()) {
                this.isDone = true;
                return;
            } else {
                AbstractCard card = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                addToTop(new StreamlineSpecificCardAction(card));
                groupCopy.remove(card);
            }
        }
        this.isDone = true;
    }
}