package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import zephyrsquallmod.actions.common.StreamlineSpecificCardAction;
import zephyrsquallmod.cards.skill.Book;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class RushOfIdeasPower extends BasePower {
    public static final String POWER_ID = makeID("RushOfIdeas");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RushOfIdeasPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof Book && card.cardID.equals(Book.ID)) {
            flash();
            // Cast the card to a Book so its recordedCards field can be accessed.
            Book book = (Book) card;
            for (AbstractCard recordedCard : book.recordedCards) {
                addToTop(new StreamlineSpecificCardAction(recordedCard));
            }
            if (amount > 1)
                addToBot(new ReducePowerAction(owner, owner, RushOfIdeasPower.POWER_ID, 1));
            else
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, RushOfIdeasPower.POWER_ID));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, RushOfIdeasPower.POWER_ID));
    }
}
