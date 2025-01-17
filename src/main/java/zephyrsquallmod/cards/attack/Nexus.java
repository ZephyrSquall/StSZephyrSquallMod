package zephyrsquallmod.cards.attack;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.NexusAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class Nexus extends BaseCard {
    public static final String ID = makeID(Nexus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final ArrayList<AbstractGameAction.AttackEffect> slashEffects = new ArrayList<>( Arrays.asList(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    private static final int DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Nexus() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            int effectIndex = MathUtils.random(slashEffects.size() - 1);
            AbstractGameAction.AttackEffect slashEffect = slashEffects.get(effectIndex);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), slashEffect));
        }
        addToBot(new NexusAction(this));
    }
}
