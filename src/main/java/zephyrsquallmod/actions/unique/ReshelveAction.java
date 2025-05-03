package zephyrsquallmod.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;

public class ReshelveAction extends AbstractGameAction {
    public ReshelveAction() {}

    public void update() {
        for (AbstractRelic r : AbstractDungeon.player.relics)
            r.onShuffle();

        AbstractDungeon.player.discardPile.shuffle(AbstractDungeon.shuffleRng);

        CardGroup cardsInitiallyInDiscardPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            cardsInitiallyInDiscardPile.addToTop(card);
        }

        int count = 0;
        for (Iterator<AbstractCard> drawPileIterator = AbstractDungeon.player.drawPile.group.iterator(); drawPileIterator.hasNext();) {
            count++;
            AbstractCard card = drawPileIterator.next();
            drawPileIterator.remove();
            // The is no soul group that mimics the appearance of shuffle souls and goes to any location other than the
            // draw pile. However, by manually giving all the cards to be moved certain parameters, the discard souls
            // can be made to look almost identical to shuffle souls.
            card.current_x = DRAW_PILE_X;
            card.current_y = DRAW_PILE_Y;
            card.angle = MathUtils.random(-10.0F, 40.0F);
            card.darken(true);
            card.shrink(true);
            if (count < 11) {
                (AbstractDungeon.getCurrRoom()).souls.discard(card, false);
            } else {
                AbstractDungeon.player.discardPile.addToTop(card);
            }
        }

        count = 0;
        for (Iterator<AbstractCard> initialDiscardPileIterator = cardsInitiallyInDiscardPile.group.iterator(); initialDiscardPileIterator.hasNext();) {
            count++;
            AbstractCard card = initialDiscardPileIterator.next();
            initialDiscardPileIterator.remove();
            AbstractDungeon.player.discardPile.removeCard(card);
            if (count < 11) {
                (AbstractDungeon.getCurrRoom()).souls.shuffle(card, false);
            } else {
                (AbstractDungeon.getCurrRoom()).souls.shuffle(card, true);
            }
        }
        this.isDone = true;
    }
}
