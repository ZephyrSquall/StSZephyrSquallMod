package zephyrsquallmod.actions;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import zephyrsquallmod.cardmodifiers.StreamlineModifier;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.*;
import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class StreamlineSpecificCardAction extends AbstractGameAction {
    private final AbstractCard targetCard;
    private final float startingDuration;

    public StreamlineSpecificCardAction(AbstractCard targetCard) {
        setValues(target, source);
        this.targetCard = targetCard;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            if (hasModifier(targetCard, makeID(StreamlineModifier.class.getSimpleName()))) {
                ArrayList<AbstractCardModifier> streamlineModifiers = getModifiers(targetCard, makeID(StreamlineModifier.class.getSimpleName()));
                AbstractCardModifier streamlineModifier = streamlineModifiers.get(0);
                ((StreamlineModifier) streamlineModifier).applyAgain(targetCard);
            } else {
                addModifier(targetCard, new StreamlineModifier());
            }

        }
        tickDuration();
    }
}