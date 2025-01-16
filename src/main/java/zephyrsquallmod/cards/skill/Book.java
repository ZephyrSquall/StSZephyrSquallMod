package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.BookAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.util.CardStats;

import java.util.ArrayList;

public class Book extends BaseCard {
    public static final String ID = makeID(Book.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int UPG_COST = 0;
    private boolean hasRecordedCards = false;
    public ArrayList<AbstractCard> recordedCards;

    public Book() {
        super(ID, info);

        this.selfRetain = true;
        this.exhaust = true;
        this.recordedCards = null;
    }

    public Book(ArrayList<AbstractCard> recordedCards) {
        super(ID, info);

        this.selfRetain = true;
        this.exhaust = true;
        this.setCostUpgrade(UPG_COST);

        this.hasRecordedCards = true;
        this.recordedCards = recordedCards;
        this.updateDescription();
    }

    public void applyPowers() {
        // Check if a Recorded card has been removed from the exhaust pile by other means. If so, it is no longer
        // Recorded (even if exhausted again before this Book is played).
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        for (AbstractCard card : this.recordedCards) {
            if (!AbstractDungeon.player.exhaustPile.contains(card))
                cardsToRemove.add(card);
        }
        // Remove cards in a separate loop to prevent ConcurrentModificationException.
        for (AbstractCard card : cardsToRemove)
            this.recordedCards.remove(card);
        if (this.recordedCards.isEmpty())
            this.hasRecordedCards = false;
        this.updateDescription();
        super.applyPowers();
    }

    private void updateDescription() {
        if (this.hasRecordedCards) {
            StringBuilder description = new StringBuilder(cardStrings.EXTENDED_DESCRIPTION[0]);
            for (int i = 0; i < this.recordedCards.size(); i++) {
                // Replace spaces with a space and asterisk so multiword card names appear in all yellow (otherwise
                // only the first word of the name would be yellow).
                description.append(this.recordedCards.get(i).name.replace(" ", " *"));
                if (i < this.recordedCards.size() - 2)
                    description.append(cardStrings.EXTENDED_DESCRIPTION[1]);
                else if (i == this.recordedCards.size() - 2)
                    description.append(cardStrings.EXTENDED_DESCRIPTION[2]);
            }
            description.append(cardStrings.EXTENDED_DESCRIPTION[3]);
            this.rawDescription = description.toString();
        } else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4];
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.hasRecordedCards) {
            addToBot(new BookAction(this.recordedCards));
            this.hasRecordedCards = false;
            updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (this.hasRecordedCards) {
            return new Book(this.recordedCards);
        } else {
            return new Book();
        }
    }
}
