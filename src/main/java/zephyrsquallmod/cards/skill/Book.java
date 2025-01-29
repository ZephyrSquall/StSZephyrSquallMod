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
    public boolean hasRecordedCards = false;
    public ArrayList<AbstractCard> recordedCards;

    public Book() {
        super(ID, info);

        this.selfRetain = true;
        this.exhaust = true;
        this.setCostUpgrade(UPG_COST);
        this.recordedCards = new ArrayList<>();
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

    public void updateDescription() {
        if (this.hasRecordedCards) {
            StringBuilder description = new StringBuilder(cardStrings.EXTENDED_DESCRIPTION[0]);
            for (int i = 0; i < this.recordedCards.size(); i++) {
                // Replace spaces with a space and asterisk so multiword card names appear in all yellow (otherwise
                // only the first word of the name would be yellow).
                description.append(this.recordedCards.get(i).name.replace(" ", " *"));
                if (i < this.recordedCards.size() - 2)
                    description.append(cardStrings.EXTENDED_DESCRIPTION[1]);
                else if (i == this.recordedCards.size() - 2)
                    // Use the Oxford comma if there are more than 2 recorded cards, don't use it if there are exactly 2
                    // recorded cards.
                    if (this.recordedCards.size() == 2)
                        description.append(cardStrings.EXTENDED_DESCRIPTION[3]);
                    else
                        description.append(cardStrings.EXTENDED_DESCRIPTION[2]);
            }
            description.append(cardStrings.EXTENDED_DESCRIPTION[4]);
            this.rawDescription = description.toString();
        } else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5];
        }
        initializeDescription();
    }

    // When applying powers for a Book, also apply powers for all monsters so their damage is correctly updated in case
    // the Book has a Recorded Glyph of Warding.
    public void applyPowers() {
        super.applyPowers();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            m.applyPowers();
    }

    // Applying powers on monsters at the end of turn shouldn't be necessary to get Glyph of Warding damage correct, but
    // this is included as a failsafe to make sure the appropriate damage is dealt if somehow all prior checks to apply
    // powers on monsters were missed. This isn't ideal as it means the enemy's damage intent will suddenly adjust as
    // the turn ends, but a sudden change like this will make it more obvious if I have a bug to track down.
    public void triggerOnEndOfPlayerTurn() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            m.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.hasRecordedCards) {
            addToBot(new BookAction(this.recordedCards));
            this.hasRecordedCards = false;
            updateDescription();
        }
    }

    // When a Book is copied, all of its Recorded cards are also copied.
    @Override
    public AbstractCard makeCopy() {
        if (this.hasRecordedCards) {
            ArrayList<AbstractCard> copiedRecordedCards = new ArrayList<>(this.recordedCards.size());
            for (AbstractCard originalRecordedCard : this.recordedCards) {
                AbstractCard copiedRecordedCard = originalRecordedCard.makeStatEquivalentCopy();
                copiedRecordedCards.add(copiedRecordedCard);
            }
            return new Book(copiedRecordedCards);
        } else {
            return new Book();
        }
    }

    // When making a new Book that is meant to be the same instance of this Book, make sure it has the exact same list
    // of Recorded cards instead of a list of copied cards.
    @Override
    public AbstractCard makeSameInstanceOf() {
        Book newBook = (Book) super.makeSameInstanceOf();
        newBook.recordedCards = this.recordedCards;
        return newBook;
    }
}
