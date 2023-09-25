package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;

import java.util.ArrayList;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class FlashOfInspirationAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("StreamlineAction"));
    public static final String[] TEXT = uiStrings.TEXT;
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
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
                this.isDone = true;
                return;
            } else {
                AbstractCard card = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                // Add to top is necessary. If multiple Flashes of Inspiration are drawn at the same time, they will add
                // all their effects to the action queue simultaneously. If addToBot were used, cards wouldn't actually
                // be Streamlined until all Flashes of Inspiration have resolved, making it possible to queue up more
                // Streamlines on a card than its cost.
                addToTop(new StreamlineSpecificCardAction(card));
                groupCopy.remove(card);
            }
        }
        this.isDone = true;
    }
}
