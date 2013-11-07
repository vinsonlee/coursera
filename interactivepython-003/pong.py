# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True

ball_pos = [None, None]
ball_vel = [None, None]
paddle1_pos = None
paddle2_pos = None
paddle1_vel = None
paddle2_vel = None
paddle_acc = 8
score1 = 0
score2 = 0


# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel  # these are vectors stored as lists

    ball_pos = [WIDTH / 2, HEIGHT / 2]
    ball_vel[0] = random.randrange(120, 240) / 40
    ball_vel[1] = -random.randrange(60, 180) / 40

    if direction == RIGHT:
        pass
    elif direction == LEFT:
        ball_vel[0] = -ball_vel[0]


# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are numbers
    global score1, score2  # these are ints

    score1 = 0
    score2 = 0
    paddle1_pos = HEIGHT / 2
    paddle2_pos = HEIGHT / 2
    paddle1_vel = 0
    paddle2_vel = 0

    spawn_ball(random.choice([LEFT, RIGHT]))


def draw(c):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel
    global paddle1_vel, paddle2_vel

    # draw mid line and gutters
    c.draw_line([WIDTH / 2, 0], [WIDTH / 2, HEIGHT], 1, "White")
    c.draw_line([PAD_WIDTH, 0], [PAD_WIDTH, HEIGHT], 1, "White")
    c.draw_line([WIDTH - PAD_WIDTH, 0], [WIDTH - PAD_WIDTH, HEIGHT], 1, "White")

    # update ball
    ball_pos[0] += ball_vel[0]
    ball_pos[1] += ball_vel[1]

    # draw ball
    c.draw_circle(ball_pos, BALL_RADIUS, 1, "White", "White")

    # collision detection
    if ball_pos[1] <= BALL_RADIUS or ball_pos[1] >= HEIGHT - 1 - BALL_RADIUS:
        ball_vel[1] = -ball_vel[1]
    elif ball_pos[0] - BALL_RADIUS <= PAD_WIDTH and ball_pos[1] >= paddle1_pos - HALF_PAD_HEIGHT and ball_pos[1] <= paddle1_pos + HALF_PAD_HEIGHT:
        ball_vel[0] = -ball_vel[0]
        ball_vel[0] *= 1.1
        ball_vel[1] *= 1.1
    elif ball_pos[0] + BALL_RADIUS >= WIDTH - 1 - PAD_WIDTH and ball_pos[1] >= paddle2_pos - HALF_PAD_HEIGHT and ball_pos[1] <= paddle2_pos + HALF_PAD_HEIGHT:
        ball_vel[0] = -ball_vel[0]
        ball_vel[0] *= 1.1
        ball_vel[1] *= 1.1
    elif ball_pos[0] - BALL_RADIUS <= PAD_WIDTH:
        global score2
        score2 += 1
        spawn_ball(RIGHT)
    elif ball_pos[0] + BALL_RADIUS >= (WIDTH - 1) - PAD_WIDTH:
        global score1
        score1 += 1
        spawn_ball(LEFT)

    # update paddle's vertical position, keep paddle on the screen
    paddle1_pos += paddle1_vel
    paddle2_pos += paddle2_vel

    paddle1_pos = max(HALF_PAD_HEIGHT, paddle1_pos)
    paddle1_pos = min(paddle1_pos, HEIGHT - 1 - HALF_PAD_HEIGHT)

    paddle2_pos = max(HALF_PAD_HEIGHT, paddle2_pos)
    paddle2_pos = min(paddle2_pos, HEIGHT - 1 - HALF_PAD_HEIGHT)

    # draw paddles
    c.draw_polygon([[0, paddle1_pos - HALF_PAD_HEIGHT],
                    [PAD_WIDTH, paddle1_pos - HALF_PAD_HEIGHT],
                    [PAD_WIDTH, paddle1_pos + HALF_PAD_HEIGHT],
                    [0, paddle1_pos + HALF_PAD_HEIGHT]
                    ], 1, "White", "White")

    c.draw_polygon([[WIDTH - 1, paddle2_pos - HALF_PAD_HEIGHT],
                    [WIDTH - 1 - PAD_WIDTH, paddle2_pos - HALF_PAD_HEIGHT],
                    [WIDTH - 1 - PAD_WIDTH, paddle2_pos + HALF_PAD_HEIGHT],
                    [WIDTH - 1, paddle2_pos + HALF_PAD_HEIGHT]
                    ], 1, "White", "White")

    # draw scores
    c.draw_text(str(score1), [WIDTH/2 - 50, 80], 40, "White")
    c.draw_text(str(score2), [WIDTH/2 + 40, 80], 40, "White")


def keydown(key):
    global paddle1_vel, paddle2_vel

    if key == simplegui.KEY_MAP["down"]:
        paddle2_vel = paddle_acc
    elif key == simplegui.KEY_MAP["up"]:
        paddle2_vel = -paddle_acc
    elif key == simplegui.KEY_MAP["s"]:
        paddle1_vel = paddle_acc
    elif key == simplegui.KEY_MAP["w"]:
        paddle1_vel = -paddle_acc


def keyup(key):
    global paddle1_vel, paddle2_vel

    if key in (simplegui.KEY_MAP["down"],
               simplegui.KEY_MAP["up"]):
        paddle2_vel = 0
    elif key in (simplegui.KEY_MAP["w"],
                 simplegui.KEY_MAP["s"]):
        paddle1_vel = 0


def restart():
    new_game()

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button("Restart", restart, 100)


# start frame
new_game()
frame.start()
