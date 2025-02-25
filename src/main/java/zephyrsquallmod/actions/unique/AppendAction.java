package zephyrsquallmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.cards.skill.Book;

import java.util.List;
import java.util.function.Consumer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class AppendAction extends AbstractGameAction {
    private static final UIStrings recordUiStrings = CardCrawlGame.languagePack.getUIString(makeID("RecordAction"));
    public static final String[] RECORD_TEXT = recordUiStrings.TEXT;
    private final int amount;

    public AppendAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        int recordableCards = AbstractDungeon.player.hand.size();
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.cardID.equals(Book.ID)) {
                recordableCards--;
            }
        }
        if (recordableCards == 0)
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, RECORD_TEXT[1], true));
        else
            addToTop(new SelectCardsInHandAction(amount, RECORD_TEXT[0], card -> !card.cardID.equals(Book.ID), appendSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> appendSelected = cardList -> {
        // AppendAction needs to use follow-up actions, since cards that are filtered out of the hand by the predicate
        // for the SelectCardsInHandAction (which in this case filters out all Books) don't get returned to the hand
        // until this action is over, however those Books need to be back in hand for this callback to work correctly.
        addToTop(new AppendFollowUpAction(cardList));
    };
}
