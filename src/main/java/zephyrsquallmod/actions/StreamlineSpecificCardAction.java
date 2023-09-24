package zephyrsquallmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import zephyrsquallmod.cardmodifiers.StreamlineModifier;

import static basemod.helpers.CardModifierManager.*;

public class StreamlineSpecificCardAction extends AbstractGameAction {
    private final AbstractCard targetCard;

    public StreamlineSpecificCardAction(AbstractCard targetCard) {
        this.targetCard = targetCard;
    }

    public void update() {
        addModifier(targetCard, new StreamlineModifier());
        this.isDone = true;
    }
}