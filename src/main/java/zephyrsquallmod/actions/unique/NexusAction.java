package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.cards.attack.Nexus;

public class NexusAction extends AbstractGameAction {
    private final AbstractCard card;

    public NexusAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        this.card.baseMagicNumber += 1;
        this.card.applyPowers();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Nexus.ID)) {
                c.baseMagicNumber += 1;
                c.applyPowers();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Nexus.ID)) {
                c.baseMagicNumber += 1;
                c.applyPowers();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Nexus.ID)) {
                c.baseMagicNumber += 1;
                c.applyPowers();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.cardID.equals(Nexus.ID)) {
                c.baseMagicNumber += 1;
                c.applyPowers();
            }
        }
        this.isDone = true;
    }
}
