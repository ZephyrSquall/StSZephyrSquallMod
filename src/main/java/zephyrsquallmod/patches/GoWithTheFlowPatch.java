package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.cards.skill.GoWithTheFlow;

// Go with the Flow wants to check the multiplier of the enemy's damage intent, so that if the enemy is doing a
// multi-hit attack, Go with the Flow will grant Block for every hit. However, the damage intent multiplier is a private
// field, and there are no public methods to get the multiplier like there is to get the intent damage amount. As such,
// the only way for Go with the Flow to get the multiplier is to patch AbstractMonster so it explicitly checks the
// player's hand for any Go with the Flow cards and directly sets its magicNumber to the damage intent multiplier.

@SpirePatch2(
        clz = AbstractMonster.class,
        method = "applyPowers"
)
public class GoWithTheFlowPatch {

    @SpireInsertPatch(
            locator = Locator.class

    )
    public static void getIntentDamageAndMultiplier(AbstractMonster __instance, int ___intentMultiAmt, boolean ___isMultiDmg) {
        if (__instance.getIntentBaseDmg() >= 0) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof GoWithTheFlow && card.cardID.equals(GoWithTheFlow.ID)) {
                    GoWithTheFlow goWithTheFlow = (GoWithTheFlow) card;
                    if (goWithTheFlow.wantsIntentInfo) {
                        goWithTheFlow.targetIsAttacking = true;
                        goWithTheFlow.baseBlock = __instance.getIntentDmg();
                        if (___isMultiDmg)
                            goWithTheFlow.baseMagicNumber = ___intentMultiAmt;
                        else
                            goWithTheFlow.baseMagicNumber = 1;
                    }
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "updateIntentTip");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
