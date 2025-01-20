package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cardmodifiers.StreamlineModifier;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

import static basemod.helpers.CardModifierManager.getModifiers;
import static basemod.helpers.CardModifierManager.hasModifier;

public class MagnumOpus extends BaseCard {
    public static final String ID = makeID(MagnumOpus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            6
    );

    private static final int DAMAGE = 30;
    private static final int UPG_DAMAGE = 6;

    public MagnumOpus() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    public void applyPowers() {
        int timesStreamlined = 0;
        if (hasModifier(this, makeID(StreamlineModifier.class.getSimpleName()))) {
            StreamlineModifier streamlineModifier = (StreamlineModifier) getModifiers(this, makeID(StreamlineModifier.class.getSimpleName())).get(0);
            timesStreamlined = streamlineModifier.totalReduction;
        }
        this.baseMagicNumber = timesStreamlined;
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        initializeDescription();
    }
}
