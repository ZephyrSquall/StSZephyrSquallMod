
package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.TailwindPower;
import zephyrsquallmod.util.CardStats;

public class StormBrewing extends BaseCard {
    public static final String ID = makeID(StormBrewing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAXIMUM_TAILWIND = 9;

    public StormBrewing() {
        super(ID, info);

        this.misc = 0;
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        setExhaust(true);

        // This card is given the HEALING tag, which prevents it from being chosen by effects that generate random cards
        // mid-combat. Usually, this tag is used to prevent the player from stalling a fight trying to generate a card
        // with a permanent effect. However, I've decided to add it to this card for essentially the exact opposite
        // reason: This card does literally nothing when played if it is randomly generated mid-combat. It needs to be
        // part of the player's master deck and accruing Tailwind between combats to start having any in-combat effects.
        tags.add(CardTags.HEALING);
    }

    public void onLoadedMisc() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
    }

    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // The makeStatEquivalentCopy method doesn't properly update magicNumber, only baseMagicNumber. Therefore, it is
        // important that the IncreaseMiscAction is called, even if it's with an increase of 0, as this action calls
        // applyPowers on all cards with a matching UUID, allowing copies of Storm Brewing (e.g. from Echo Form) to
        // properly update their magicNumber before they are played (this is also exactly why copies of Genetic
        // Algorithm manage to properly update their block value).
        int misc_increase = (upgraded || this.misc + 1 <= MAXIMUM_TAILWIND) ? 1 : 0;
        addToBot(new IncreaseMiscAction(this.uuid, this.misc, misc_increase));

        // Applying 0 Tailwind creates a version of that power with 0 amount, which technically works correctly but
        // looks visually buggy. So skip applying the power if the Tailwind to be applied is 0.
        if (magicNumber > 0) {
            addToBot(new ApplyPowerAction(p, p, new TailwindPower(p, magicNumber)));
        }
    }
}
