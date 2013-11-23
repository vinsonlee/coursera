# Testing template for the Card class


import random

# define globals for cards
SUITS = ['C', 'S', 'H', 'D']
RANKS = ['A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K']
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


#################################################
# Student should insert the implementation of the Card class here

    
###################################################
# Test code

c1 = Card("S", "A")
print c1
print c1.get_suit(), c1.get_rank()
print type(c1)

c2 = Card("C", "2")
print c2
print c2.get_suit(), c2.get_rank()
print type(c2)

c3 = Card("D", "T")
print c3
print c3.get_suit(), c3.get_rank()
print type(c3)


###################################################
# Output to console

#SA
#S A
#<class '__main__.Card'>
#C2
#C 2
#<class '__main__.Card'>
#DT
#D T
#<class '__main__.Card'>
