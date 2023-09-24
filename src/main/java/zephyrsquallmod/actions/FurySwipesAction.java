package zephyrsquallmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FurySwipesAction extends AbstractGameAction {

    public FurySwipesAction() {}

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(AbstractCard.CardTags.STRIKE))
                addToTop(new StreamlineSpecificCardAction(c));
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(AbstractCard.CardTags.STRIKE))
                addToTop(new StreamlineSpecificCardAction(c));
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(AbstractCard.CardTags.STRIKE))
                addToTop(new StreamlineSpecificCardAction(c));
        }
        this.isDone = true;
    }
}