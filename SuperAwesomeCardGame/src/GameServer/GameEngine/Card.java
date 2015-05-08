package GameServer.GameEngine;

import java.io.Serializable;
import java.sql.SQLException;

import GameServer.DBHelper;

import com.mysql.jdbc.ResultSet;

public final class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	//Non mechanical properties
	final private String name;
	final private String description;
	final private String imagePath;
	final private String cardType;
	
	//Non-action related properties
	final private int costStealth;
	final private int costAttack;
	final private int vp;
	final private int attack;
	final private int stealth;	
	
	//Action related properties
	final private int preturnDiscard;
	final private int postturnDiscard;
	final private int drawCards;
	final private int othersDrawCards;
	final private int trashCardsMandatory;
	final private int trashCardsOptional;
	final private int trashForAttack;
	final private int trashForStealth;
	final private int removeFromPlayArea;
	final private int othersDiscard;
	final private int othersLoseVP;
	final private boolean takeAnotherTurn;
	final private boolean refreshPlayArea;
	final private boolean trashAfterUse;
	
	
	/**
	 * Creates a card object
	 * @param name Name of the card
	 * @param description Description of the card
	 * @param costStealth Cost to buy with Stealth
	 * @param costAttack Cost to defeat in an attack
	 * @param vp Victory Points
	 * @param attack The attack power of the card.
	 * @param stealth The amount of stealth (currency).
	 * @param preturnDiscard Number of cards to discard prior to other action effects.
	 * @param postturnDiscard Number of cards to discard after other action effects.
	 * @param drawCards Number of cards to draw.
	 * @param othersDrawCards Number of cards other players draw.
	 * @param trashCardsMandatory Number of cards that must be trashed.
	 * @param trashCardsOptional Number of cards that may be trashed.
	 * @param trashForAttack Number of cards to trash for attack. 
	 * @param trashForStealth	Number of cards to trash for attack.
	 * @param removeFromPlayArea Number of cards to discard from play area.
	 * @param othersDiscard Number of cards other players must discard down to.
	 * @param othersLoseVP Number of VPs other players lose.
	 * @param takeAnotherTurn If true, then the player can take another turn after this one.
	 * @param refreshPlayArea Discard all cards in the play area and replace.
	 * @param trashAfterUse
	 */
	
	public Card(String name, String description, String imagePath, String cardType, int costBuy, int costAttack,
			int vp, int attack, int stealth, int preturnDiscard,
			int postturnDiscard, int drawCards, int othersDrawCards,
			int trashCardsMandatory, int trashCardsOptional, int trashForAttack, int trashForStealth, int removeFromPlayArea,
			int othersDiscard, int giveCurseCards, boolean takeAnotherTurn, boolean refreshPlayArea,
			boolean trashAfterUse) {
		super();
		this.name = name;
		this.description = description;
		this.imagePath = imagePath;
		this.cardType = cardType;
		this.costStealth = costBuy;
		this.costAttack = costAttack;
		this.vp = vp;
		this.attack = attack;
		this.stealth = stealth;
		this.preturnDiscard = preturnDiscard;
		this.postturnDiscard = postturnDiscard;
		this.drawCards = drawCards;
		this.othersDrawCards = othersDrawCards;
		this.trashCardsMandatory = trashCardsMandatory;
		this.trashCardsOptional = trashCardsOptional;
		this.trashForAttack = trashForAttack;
		this.trashForStealth = trashForStealth;
		this.removeFromPlayArea = removeFromPlayArea;
		this.othersDiscard = othersDiscard;
		this.othersLoseVP = giveCurseCards;
		this.takeAnotherTurn = takeAnotherTurn;
		this.refreshPlayArea = refreshPlayArea;
		this.trashAfterUse = trashAfterUse;
	}
	
	/**
	 * Constructor to pull the card information from the database from the given card name
	 * @param cardName Name of the desired card
	 * @throws SQLException
	 */
	
	public Card(String cardName) throws SQLException{
		
		super();
		DBHelper dbh = new DBHelper();
		String query = "SELECT * FROM Cards WHERE Name = '" + cardName + "'";
		java.sql.ResultSet rs = dbh.executeQuery(query);
		
		
		rs.next();
		
		this.name = rs.getString("Name");
		this.description = rs.getString("Description");
		this.imagePath = rs.getString("ImagePath");
		this.cardType = rs.getString("CardType");
		this.costStealth = rs.getInt("CostBuy");
		this.costAttack = rs.getInt("CostAttack");
		this.vp = rs.getInt("VP");
		this.attack = rs.getInt("Attack");
		this.stealth = rs.getInt("Stealth");
		this.preturnDiscard = rs.getInt("PreturnDiscard");
		this.postturnDiscard = rs.getInt("PostturnDiscard");
		this.drawCards = rs.getInt("DrawCards");
		this.othersDrawCards = rs.getInt("OthersDrawCards");
		this.trashCardsMandatory = rs.getInt("TrashCardsMandatory");
		this.trashCardsOptional = rs.getInt("TrashCardsOptional");
		this.trashForAttack = rs.getInt("TrashForAttack");
		this.trashForStealth = rs.getInt("TrashForStealth");
		this.removeFromPlayArea = rs.getInt("RemoveFromPlayArea");
		this.othersDiscard = rs.getInt("OthersDiscard");
		this.othersLoseVP = rs.getInt("OthersLoseVP");
		this.takeAnotherTurn = rs.getBoolean("TakeAnotherTurn");
		this.refreshPlayArea = rs.getBoolean("RefreshPlayArea");
		this.trashAfterUse = rs.getBoolean("TrashAfterUse");
		
	}

	/**
	 * Returns the name of the card, as it should be printed on the card.
	 * @return the name of the card
	 */
	
	public String getName() {
		return name;
	}


	/**
	 * Returns the human readable description of the card.
	 * @return the description of the card
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the image path of the card.
	 * @return image path
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Gets the type of card
	 * @return card type
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * The cost to buy the card with stealth.
	 * @return the cost to buy the card
	 */
	public int getCostStealth() {
		return costStealth;
	}


	/**
	 * The cost to defeat the card with attack.
	 * @return the cost to defeat a card
	 */
	public int getCostAttack() {
		return costAttack;
	}


	/**
	 * The victory points the card is worth at the end of the game.
	 * @return the number of victory points
	 */
	public int getVp() {
		return vp;
	}

	/**
	 * The power of the attack the card gives.
	 * @return the amount of power
	 */
	public int getAttack() {
		return attack;
	}
	
	/**
	 * The stealth value the card gives
	 * @return amount of stealth
	 */
	public int getStealth() {
		return stealth;
	}

	/**
	 * Number of cards to discard prior to other action effects listed on the card. Discarding is split since for some combinations discarding before has a significant difference from discarding after. 
	 * @return the number of cards to discard before actions
	 */
	public int getPreturnDiscard() {
		return preturnDiscard;
	}


	/**
	 * Number of cards to discard after other action effects listed on the card. Discarding is split since for some combinations discarding before has a significant difference from discarding after. 
	 * @return the number of cards to discard after actions
	 */
	public int getPostturnDiscard() {
		return postturnDiscard;
	}


	/**
	 * The number of cards the user draws from their deck.
	 * @return the number of cards to draw
	 */
	public int getDrawCards() {
		return drawCards;
	}


	/**
	 * The number of cards other players will draw because of this card.
	 * @return the number of cards other players draw
	 */
	public int getOthersDrawCards() {
		return othersDrawCards;
	}


	/**
	 * The number of cards the player must trash.
	 * @return the number of cards to trash
	 */
	public int getTrashCardsMandatory() {
		return trashCardsMandatory;
	}


	/**
	 * The player may trash up to this many cards.
	 * @return the number of cards to trash
	 */
	public int getTrashCardsOptional() {
		return trashCardsOptional;
	}


	/**
	 * The number of cards to trash card for power based on the original cost of the card.
	 * @return number of cards to trash for power
	 */
	public int getTrashForAttack() {
		return trashForAttack;
	}
	
	/**
	 * The number of cards to trash for stealth based on the original cost of the card.
	 * @return number of cards to trash for power
	 */
	public int getTrashForStealth() {
		return trashForStealth;
	}
	
	/**
	 * @param removeFromPlayArea Number of cards to discard from play area.
	 * @param othersDiscard Number of cards other players must discard down to.
	 * @param takeAnotherTurn If true, then the player can take another turn after this one.
	 * @param refreshPlayArea Discard all cards in the play area and replace.
	 */

	/**
	 * This allows the player to remove and replace this number of cards from the play area.
	 * @return the number of cards to remove and replace from play area
	 */
	public int getRemoveFromPlayArea() {
		return removeFromPlayArea;
	}


	/**
	 * The number of cards the other players must discard down to.
	 * @return the number of cards to discard down to
	 */
	public int getOthersDiscard() {
		return othersDiscard;
	}


	/**
	 * If true, then the card allows the player to take another turn immediatly following this one.
	 * @return true if it grants player another turn
	 */
	public boolean isTakeAnotherTurn() {
		return takeAnotherTurn;
	}


	/**
	 * If true, then the player can refresh all the cards in the play area.
	 * @return true if it causes play area to refresh
	 */
	public boolean isRefreshPlayArea() {
		return refreshPlayArea;
	}


	/**
	 * Gives the number of VPs that the card takes away from other players
	 * @return number of VPs to take from other players
	 */
	public int getOthersLoseVP() {
		return othersLoseVP;
	}


	/**True if the card is trashed after use, false otherwise
	 * @return trashAfterUse
	 */
	public boolean isTrashAfterUse() {
		return trashAfterUse;
	}
	
}
