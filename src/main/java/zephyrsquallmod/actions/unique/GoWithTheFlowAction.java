package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class GoWithTheFlowAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private final boolean targetIsAttacking;
    private final int block;
    private final int amount;

    public GoWithTheFlowAction(boolean targetIsAttacking, int block, int amount) {
        this.targetIsAttacking = targetIsAttacking;
        this.block = block;
        this.amount = amount;
    }

    public void update() {
        if (targetIsAttacking) {
            for (int i = 0; i < amount; i++)
                addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
        this.isDone = true;
    }
}