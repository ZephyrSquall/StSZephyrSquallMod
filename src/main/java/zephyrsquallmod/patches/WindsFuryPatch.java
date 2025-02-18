package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.actions.unique.WindsFuryAction;
import zephyrsquallmod.powers.WindsFuryPower;

@SpirePatch2(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class WindsFuryPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c, monster"}
    )
    public static void zeroOutBlockAmount(AbstractCard c, AbstractMonster monster) {
        if (c.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasPower(WindsFuryPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new WindsFuryAction(c, monster));
        }
    }

    // This inserts the WindsFuryAction after the use method of the card is called but before the UseCardAction is added
    // to the bottom of the action queue, making sure the extra attacks occur as part of playing the card but after all
    // other actions that card performs.
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
