package zephyrsquallmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

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
        if (!card.isCostModified) {
            originallyModified = false;
            originalCost = card.cost;
        }
        reduceCost(card);
    }

    // Do not put multiple applications of StreamlineModifier on a single card, as this interferes with the ability to
    // properly detect if the card had its cost modified by any other sources. This prevents onRemove from setting
    // card.isCostModified back to false, resulting in the cost number remaining green even when it's returned to its
    // original value. If a card is Streamlined again while already Streamlined, call applyAgain on the original
    // StreamlineModifier instead of applying a second StreamlineModifier.
    public void applyAgain(AbstractCard card) {
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
