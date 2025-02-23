package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.cards.skill.Book;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.RushOfIdeasPower;
import zephyrsquallmod.util.CardStats;

public class RushOfIdeas extends BaseCard {
    public static final String ID = makeID(RushOfIdeas.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;

    public RushOfIdeas() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        this.cardsToPreview = new Book();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
        addToBot(new ApplyPowerAction(p, p, new RushOfIdeasPower(p)));
    }
}
