# implementation of card game - Memory

import simplegui
import random

# helper function to initialize globals
def new_game():
    global cards, exposed
    cards = range(8) + range(8)
    exposed = [False] * 16
    exposed[0] = True
    exposed[10] = True
    random.shuffle(cards)
     
# define event handlers
def mouseclick(pos):
    # add game state logic here
    pass
    
                        
# cards are logically 50x100 pixels in size    
def draw(canvas):
    global cards, exposed
    CARD_WIDTH = 50
    CARD_HEIGHT = 100
    for i in range(len(cards)):
        if exposed[i]:
            canvas.draw_text(str(cards[i]), (i * CARD_WIDTH + 12, 60), 40, "White")
        else:
            canvas.draw_polygon([(CARD_WIDTH * i, 0),
                                (CARD_WIDTH * (i + 1), 0),
                                (CARD_WIDTH * (i + 1), CARD_HEIGHT),
                                (CARD_WIDTH * i, CARD_HEIGHT)],
                                1, "Silver", "Green")
        

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