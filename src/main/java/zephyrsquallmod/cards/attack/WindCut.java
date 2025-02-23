package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.patches.WindCutPatch;
import zephyrsquallmod.util.CardStats;

public class WindCut extends BaseCard {
    public static final String ID = makeID(WindCut.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_COST = 1;

    public WindCut() {
        super(ID, info);

        setDamage(DAMAGE);
        setCostUpgrade(UPG_COST);
    }

    public void applyPowers() {
        this.baseDamage = ZephyrSquallMod.tailwindGained;
        super.applyPowers();
        // If any Headwind has been applied to any enemy, add a "+" to the card description to indicate that the amount
        // may be higher when hovering over an enemy.
        boolean hasAppliedHeadwind = false;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && WindCutPatch.headwindApplied.get(m) > 0) {
                hasAppliedHeadwind = true;
                break;
            }
        }
        this.rawDescription = hasAppliedHeadwind ?
                cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1] :
                cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = ZephyrSquallMod.tailwindGained + WindCutPatch.headwindApplied.get(mo);
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.baseDamage = ZephyrSquallMod.tailwindGained;
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}
