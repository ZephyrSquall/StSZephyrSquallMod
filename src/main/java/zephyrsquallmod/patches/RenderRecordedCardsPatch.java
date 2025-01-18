package zephyrsquallmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.cards.skill.Book;

import static com.megacrit.cardcrawl.cards.AbstractCard.IMG_HEIGHT;
import static com.megacrit.cardcrawl.cards.AbstractCard.IMG_WIDTH;

@SpirePatch2(
        clz = AbstractCard.class,
        method = "renderCardTip"
)
public class RenderRecordedCardsPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"sb"}
    )
    public static void RenderRecordedCards(AbstractCard __instance, SpriteBatch sb) {
        if (__instance instanceof Book && __instance.cardID.equals(Book.ID)) {
            // Cast to a Book to access Book-specific fields.
            Book thisBook = (Book) __instance;
            if (thisBook.hasRecordedCards && !thisBook.recordedCards.isEmpty()) {
                float tmpScale = thisBook.drawScale * 0.8F;
                // Find the position that a preview card would be displayed if there were only one recorded card. This y
                // value is the final y value for all recorded cards, but the x value will need to be shifted for each
                // individual card depending on its index and the total number of recorded cards.
                float recordedCardsBase_x = thisBook.current_x;
                float recordedCards_y = thisBook.current_y + (IMG_HEIGHT / 2.0F + IMG_HEIGHT / 2.0F * 0.8F + 16.0F) * thisBook.drawScale;

                for (int i = 0; i < thisBook.recordedCards.size(); i++) {
                    AbstractCard recordedCardPreview = thisBook.recordedCards.get(i).makeStatEquivalentCopy();
                    // Half of the number of recorded cards gives the midway index, then subtracting this from the
                    // card's index gives how far away the card is from the midway index. 1 is subtracted from the
                    // number of recorded cards first because the maximum index is one less than the size. The midway
                    // index isn't necessarily a whole number; it will be halfway between integer indexes if there is an
                    // even number of recorded cards, indicating that cards need to be shifted by half the width of a
                    // card.
                    float offset_x = i - ((thisBook.recordedCards.size() - 1.0F) / 2.0F);
                    recordedCardPreview.current_x = recordedCardsBase_x + (IMG_WIDTH * 0.8F + 16.0F) * offset_x * thisBook.drawScale;
                    recordedCardPreview.current_y = recordedCards_y;
                    recordedCardPreview.drawScale = tmpScale;
                    recordedCardPreview.render(sb);
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "cardsToPreview");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
