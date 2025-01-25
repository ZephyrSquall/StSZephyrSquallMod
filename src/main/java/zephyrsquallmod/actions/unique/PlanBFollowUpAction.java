package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static com.megacrit.cardcrawl.actions.GameActionManager.totalDiscardedThisTurn;

public class PlanBFollowUpAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int drawsPerDiscard;
    private final int initialTotalDiscardedThisTurn;

    public PlanBFollowUpAction(AbstractPlayer p, int drawsPerDiscard, int initialTotalDiscardedThisTurn) {
        this.p = p;
        this.drawsPerDiscard = drawsPerDiscard;
        this.initialTotalDiscardedThisTurn = initialTotalDiscardedThisTurn;
    }

    public void update() {
        int cardsToDraw = drawsPerDiscard * (totalDiscardedThisTurn - initialTotalDiscardedThisTurn);
        addToTop(new DrawCardAction(p, cardsToDraw));
        this.isDone = true;
    }
 }
