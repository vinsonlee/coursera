# implementation of card game - Memory

import simplegui
import random

CARD_WIDTH = 50
CARD_HEIGHT = 100

turns = None
state = None
cards = None
exposed = None
flipped_cards_pos = []


# helper function to initialize globals
def new_game():
    global cards, exposed, state, turns
    state = 0
    turns = 0
    label.set_text("Turns = " + str(turns))
    cards = range(8) + range(8)
    exposed = [False] * 16
    random.shuffle(cards)


# define event handlers
def mouseclick(pos):
    global state, turns

    card_pos = pos[0] / CARD_WIDTH

    if not exposed[card_pos]:
        exposed[card_pos] = True
        flipped_cards_pos.append(card_pos)

        if state == 0:
            state = 1
        elif state == 1:
            state = 2
            turns += 1
            label.set_text("Turns = " + str(turns))
        elif state == 2:
            state = 1
            card0_pos = flipped_cards_pos.pop(0)
            card1_pos = flipped_cards_pos.pop(0)
            card0_val = cards[card0_pos]
            card1_val = cards[card1_pos]

            if card0_val != card1_val:
                exposed[card0_pos] = False
                exposed[card1_pos] = False
        else:
            assert False


# cards are logically 50x100 pixels in size
def draw(canvas):
    for i in range(len(cards)):
        if exposed[i]:
            canvas.draw_text(str(cards[i]),
                             (i * CARD_WIDTH + 12, 60), 40, "White")
        else:
            canvas.draw_polygon([(CARD_WIDTH * i, 0),
                                (CARD_WIDTH * (i + 1), 0),
                                (CARD_WIDTH * (i + 1), CARD_HEIGHT),
                                (CARD_WIDTH * i, CARD_HEIGHT)],
                                2, "Silver", "Green")


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Restart", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()


# Always remember to review the grading rubric
