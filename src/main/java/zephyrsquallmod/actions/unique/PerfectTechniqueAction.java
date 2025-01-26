package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;

public class PerfectTechniqueAction extends AbstractGameAction {
    public PerfectTechniqueAction() {}

    public void update() {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            addToTop(new StreamlineSpecificCardAction(card));
        }
        this.isDone = true;
    }
}
