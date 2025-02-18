package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.powers.WindsFuryPower;

public class WindsFuryAction extends AbstractGameAction {
    private final AbstractCard card;
    private final AbstractMonster target;

    public WindsFuryAction(AbstractCard card, AbstractMonster target) {
        this.card = card;
        this.target = target;
    }

    public void update() {
        if (AbstractDungeon.player.hasPower(WindsFuryPower.POWER_ID)) {
            AbstractPower windsFury = AbstractDungeon.player.getPower(WindsFuryPower.POWER_ID);
            windsFury.flash();
            for (int i = 0; i < windsFury.amount; i++) {
                // If there is no target, then either the card damages all enemies or a random enemy. For each Attack,
                // the last target of an individual hit is tracked, so the existence of a specific target is used to
                // determine if the Attack is hitting targets randomly. This unfortunately means if a card that deals
                // damage to a random enemy somehow hits 0 times, it won't be detected as a random target card and will
                // erroneously hit all enemies. Unless every card is individually tagged with whether it's a multi-hit
                // or a random-hit card, there doesn't seem to be any way to resolve this. I make a special exception
                // for the base game card Thunder Strike, the only base game Attack that deals damage to random enemies
                // and is capable of hitting 0 times, which means all base game cards and all of Zephyr's cards should
                // work correctly. The only way this can cause an issue is with other modded characters if their card
                // pool is somehow mixed with Zephyr's cards, and they also have an Attack that hits random enemies and
                // can hit 0 times, which is such a niche case that I'm fine leaving it unresolved. If any egregious
                // situations pop up, another exception can be added for that mod, like with Thunder Strike.
                if (target == null) {
                    if (ZephyrSquallMod.lastAttackCardTarget == null && !card.cardID.equals("Thunder Strike")) {
                        addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, card.multiDamage, card.damageTypeForTurn, ZephyrSquallMod.getRandomSlashEffect()));
                    } else {
                        addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
                    }
                } else {
                    addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
                }
            }
        }
        this.isDone = true;
    }
}