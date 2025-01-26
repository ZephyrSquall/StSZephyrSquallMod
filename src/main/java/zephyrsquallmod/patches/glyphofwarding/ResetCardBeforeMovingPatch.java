package zephyrsquallmod.patches.glyphofwarding;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.skill.Book;

@SpirePatch2(
        clz = CardGroup.class,
        method = "resetCardBeforeMoving"
)
public class ResetCardBeforeMovingPatch {
    @SpirePostfixPatch
    public static void applyPowersOnMonstersIfBook(CardGroup __instance, AbstractCard c) {
        // If a Book was in the player's hand and it's getting moved anywhere else, call applyPowers on all monsters
        // after it is removed from the hand, so each monster's attack damage is updated appropriately in case the Book
        // Recorded a Glyph of Warding. A card is removed from its original group at the end of the
        // resetCardBeforeMoving method, so insert this check at the very end of this method. (No equivalent patch is
        // needed for when the Book is initially generated or moved into the player's hand from anywhere, as using the
        // Book's applyPowers method to call each monsters' applyPowers method handles all of these cases.)
        if (__instance.type == CardGroup.CardGroupType.HAND && c.cardID.equals(Book.ID)) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                m.applyPowers();
        }
    }
}
