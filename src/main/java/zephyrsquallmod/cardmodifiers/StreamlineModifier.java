package zephyrsquallmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import zephyrsquallmod.ZephyrSquallMod;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.getModifiers;
import static basemod.helpers.CardModifierManager.removeSpecificModifier;
import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class StreamlineModifier extends AbstractCardModifier {
    private int totalReduction = 0;
    private boolean originallyModified = true;
    private int originalCost;

    public StreamlineModifier() {}

    @Override
    public String identifier(AbstractCard card) {
        return makeID(StreamlineModifier.class.getSimpleName());
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        // Check if the card already costs 0 or less (i.e. -1 which means X or -2 which means no cost). Cards that cost
        // 0 or less cannot be Streamlined further (they still count as Streamlined if they were previously Streamlined,
        // but won't start counting as Streamlined if they weren't already Streamlined). In this case, simply remove
        // this modifier immediately with no effect.
        if (!ZephyrSquallMod.canBeStreamlined.test(card)) {
            removeSpecificModifier(card, this, false);
            return;
        }

        // Check if the card is already Streamlined. In this case, find the original StreamlineModifier attached to this
        // card, call its applyAgain method, then remove this modifier with no further effects. It's important to make
        // sure a card is only ever affected by one Streamline modifier at a time so that when the card is played,
        // the card's original cost can be restored properly (this is particularly important to make sure the cost of a
        // card stops displaying in green, but only if no other effects have also changed the cost).
        ArrayList<AbstractCardModifier> streamlineModifiers = getModifiers(card, makeID(StreamlineModifier.class.getSimpleName()));
        if (streamlineModifiers.size() >= 2) {
            // If this ArrayList has 2 elements, that means this modifier and an already-existing modifier were found,
            // so the card was already Streamlined. Get the StreamlineModifier that isn't this one and call applyAgain
            // on it.
            for (AbstractCardModifier streamlineModifier : streamlineModifiers) {
                if (streamlineModifier != this) {
                    ((StreamlineModifier) streamlineModifier).applyAgain(card);
                }
            }
            removeSpecificModifier(card, this, false);
            return;
        }

        // If this part of the code is reached, this card costs at least 1 energy and hasn't been Streamlined
        // previously, so set up the Streamline modifier properly.
        if (!card.isCostModified) {
            originallyModified = false;
            originalCost = card.cost;
        }
        reduceCost(card);
    }

    public void applyAgain(AbstractCard card) {
        if (ZephyrSquallMod.canBeStreamlined.test(card))
            reduceCost(card);
    }

    private void reduceCost(AbstractCard card) {
        int initialCost = card.cost;
        card.updateCost(-1);
        totalReduction += initialCost - card.cost;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.updateCost(totalReduction);
        if (!originallyModified && card.cost == originalCost)
            card.isCostModified = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new StreamlineModifier();
    }
}
