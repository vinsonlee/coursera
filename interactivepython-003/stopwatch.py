# template for "Stopwatch: The Game"
import simplegui

# define global variables
timer = None
interval = 100
ticks = 0
total = 0
wins = 0


# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    tenths = t % 10
    seconds = t % 600 / 10
    minutes = t / 600

    s = str(minutes) + ":"
    if seconds < 10:
        s += "0"
    s += str(seconds) + "." + str(tenths)

    return s


# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    timer.start()


def stop():
    global total
    global wins
    if timer.is_running():
        timer.stop()
        total += 1
        if ticks % 10 == 0:
            wins += 1


def reset():
    timer.stop()
    global ticks
    global wins
    global total
    ticks = 0
    wins = 0
    total = 0


# define event handler for timer with 0.1 sec interval
def tick():
    global ticks
    ticks += 1


# define draw handler
def draw(canvas):
    canvas.draw_text(format(ticks), (100, 110), 50, "White")
    canvas.draw_text(str(wins) + "/" + str(total), (250, 30), 30, "Green")


# create frame
frame = simplegui.create_frame("Stopwatch: The Game", 300, 200)

# register event handlers
frame.set_draw_handler(draw)
frame.add_button("Start", start, 100)
frame.add_button("Stop", stop, 100)
frame.add_button("Reset", reset, 100)
timer = simplegui.create_timer(interval, tick)


# start frame
frame.start()


# Please remember to review the grading rubric
