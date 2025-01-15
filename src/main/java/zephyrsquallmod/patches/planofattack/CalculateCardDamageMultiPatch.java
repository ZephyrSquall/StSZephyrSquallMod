package zephyrsquallmod.patches.planofattack;

import com.evacipated.cardcrawl.modthespire.finders.MatchFinderExprEditor;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.patches.finders.LastInOrderFinder;

import java.util.ArrayList;
import java.util.List;

// Increase the actual damage dealt by multiple-target Attack cards.
@SpirePatch2(
        clz = AbstractCard.class,
        method = "calculateCardDamage"
)
public class CalculateCardDamageMultiPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp", "i", "damageTypeForTurn", "cardID"}
    )
    // Arrays are inherently passed by reference, so @ByRef isn't needed here to change tmp in calculateCardDamage().
    public static void checkPlanOfAttack(float[] tmp, int i, DamageInfo.DamageType damageTypeForTurn, String cardID) {
        tmp[i] = ZephyrSquallMod.addPlanOfAttackDamage(tmp[i], damageTypeForTurn, cardID);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            List<Matcher> expectedMatches = new ArrayList<>();
            MatchFinderExprEditor editor = new LastInOrderFinder(expectedMatches, finalMatcher);
            ctMethodToPatch.instrument(editor);
            if (!editor.didFindLocation()) {
                throw new PatchingException(ctMethodToPatch, "Location matching given description could not be found for patch");
            } else {
                return editor.getFoundLocations();
            }
        }
    }
}
