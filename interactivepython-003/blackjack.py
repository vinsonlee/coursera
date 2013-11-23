# Mini-project #6 - Blackjack

import simplegui
import random


# load card sprite - 949x392 - source: jfitz.com
CARD_SIZE = (73, 98)
CARD_CENTER = (36.5, 49)
card_images = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/cards.jfitz.png")

CARD_BACK_SIZE = (71, 96)
CARD_BACK_CENTER = (35.5, 48)
card_back = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/card_back.png")

# initialize some useful global variables
in_play = False
outcome = ""
score = 0
deck = None
player_hand = None
dealer_hand = None

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank),
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)


# define hand class
class Hand:
    def __init__(self):
        # create Hand object
        self.cards = []

    def __str__(self):
        # return a string representation of a hand
        s = "Hand contains"
        for card in self.cards:
            s += " " + str(card)
        return s

    def add_card(self, card):
        # add a card object to a hand
        self.cards.append(card)

    def get_value(self):
        # compute the value of the hand, see Blackjack video
        value = 0
        has_ace = False

        for card in self.cards:
            rank = card.get_rank()
            if rank == 'A':
                has_ace = True
            value += VALUES[rank]

        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        if has_ace and value + 10 <= 21:
            value += 10

        return value

    def draw(self, canvas, pos):
        pass  # draw a hand on the canvas, use the draw method for cards


# define deck class
class Deck:
    def __init__(self):
        # create a Deck object
        self.cards = []
        for suit in SUITS:
            for rank in RANKS:
                self.cards.append(Card(suit, rank))

    def shuffle(self):
        # shuffle the deck
        random.shuffle(self.cards)

    def deal_card(self):
        # deal a card object from the deck
        return self.cards.pop()

    def __str__(self):
        # return a string representing the deck
        s = "Deck contains"
        for card in self.cards:
            s += " " + str(card)
        return s


#define event handlers for buttons
def deal():
    global outcome, in_play, score, deck, player_hand, dealer_hand

    # your code goes here
    deck = Deck()
    deck.shuffle()
    player_hand = Hand()
    dealer_hand = Hand()
    player_hand.add_card(deck.deal_card())
    dealer_hand.add_card(deck.deal_card())
    player_hand.add_card(deck.deal_card())
    dealer_hand.add_card(deck.deal_card())
    in_play = True
    print "Player:", player_hand.get_value(), player_hand
    print "Dealer:", dealer_hand.get_value(), dealer_hand


def hit():
    global outcome, in_play, score

    # if the hand is in play, hit the player
    if in_play:
        print "in play!"
        player_hand.add_card(deck.deal_card())

        # if busted, assign a message to outcome, update in_play and score
        if player_hand.get_value() > 21:
            outcome = "You have busted."
            in_play = False
            score -= 1

    print "Player:", player_hand.get_value(), player_hand
    print "Dealer:", dealer_hand.get_value(), dealer_hand


def stand():
    print "stand"
    global in_play, outcome, score
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more
    if in_play:
        assert player_hand.get_value() <= 21

        while dealer_hand.get_value() < 17:
            dealer_hand.add_card(deck.deal_card())

        if dealer_hand.get_value() > 21:
            outcome = "Dealer busted."
            score += 1
        elif player_hand.get_value() > dealer_hand.get_value():
            score += 1
        else:
            score -= 1

        in_play = False
    # assign a message to outcome, update in_play and score

    print "Player:", player_hand.get_value(), player_hand
    print "Dealer:", dealer_hand.get_value(), dealer_hand


# draw handler
def draw(canvas):
    # test to make sure that card.draw works, replace with your code below

    card = Card("S", "A")
    card.draw(canvas, [300, 300])

# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit", hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)

# get things rolling
deal()

frame.start()

#import sys
#sys.exit()

# remember to review the gradic rubric
