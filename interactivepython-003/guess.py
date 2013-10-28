# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console

import math
import random
import simplegui


# initialize global variables used in your code
secret_number = None
num_range = 100
remaining_guesses = None


# helper function to start and restart the game
def new_game():
    global num_range
    global secret_number
    secret_number = random.randrange(num_range)

    global remaining_guesses
    remaining_guesses = int(math.ceil(math.log(num_range + 1, 2)))

    #secret_number = 28
    #print "secret_number is", secret_number

    print "New game. Range is from 0 to", num_range
    print "Number of remaining guesses is", remaining_guesses
    print


# define event handlers for control panel
def range100():
    global num_range
    num_range = 100
    new_game()


def range1000():
    global num_range
    num_range = 1000

    new_game()


def input_guess(guess):
    global secret_number
    global remaining_guesses

    completed_game = False

    guess = int(guess)
    print "Guess was", guess

    remaining_guesses -= 1
    print "Number of remaining guesses is", remaining_guesses

    if guess == secret_number:
        print "Correct!"
        print
        completed_game = True
    elif remaining_guesses <= 0:
        print "You ran out of guesses. The number was", secret_number
        print
        completed_game = True
    elif guess > secret_number:
        print "Lower!"
        print
    else:
        print "Higher!"
        print

    if completed_game:
        new_game()


# create frame
frame = simplegui.create_frame("Guess the Number", 300, 300)

# register event handlers for control elements
frame.add_input("Guess", input_guess, 100)
frame.add_button("Range 0 - 100", range100, 100)
frame.add_button("Range 0 - 1000", range1000, 100)

# call new_game and start frame
new_game()
frame.start()


# always remember to check your completed program against the grading rubric
