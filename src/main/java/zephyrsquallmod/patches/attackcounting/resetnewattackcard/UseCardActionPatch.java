package zephyrsquallmod.patches.attackcounting.resetnewattackcard;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

@SpirePatch2(
        clz = UseCardAction.class,
        method = "update"
)
public class UseCardActionPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void resetNewAttackCard() {
        ZephyrSquallMod.lastAttackCardTarget = null;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "exhaustOnUseOnce");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
