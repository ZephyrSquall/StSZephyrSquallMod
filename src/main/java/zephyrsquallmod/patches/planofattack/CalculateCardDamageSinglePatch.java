package zephyrsquallmod.patches.planofattack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// Increase the actual damage dealt by single-target Attack cards.
@SpirePatch2(
        clz = AbstractCard.class,
        method = "calculateCardDamage"
)
public class CalculateCardDamageSinglePatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp", "damageTypeForTurn", "cardID"}
    )
    public static void checkPlanOfAttack(@ByRef float[] tmp, DamageInfo.DamageType damageTypeForTurn, String cardID) {
        tmp[0] = ZephyrSquallMod.addPlanOfAttackDamage(tmp[0], damageTypeForTurn, cardID);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
