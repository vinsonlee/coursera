#!/usr/bin/env python

# https://class.coursera.org/crypto-009/forum/thread?thread_id=467#post-3213

from Crypto.Cipher import AES
from Crypto import Random
from Crypto.Util import strxor

def xor(x, y):
  return strxor.strxor(x, y)

def AESE(k, t):
  return AES.new(k, AES.MODE_ECB).encrypt(t)

def AESD(k, t):
  return AES.new(k, AES.MODE_ECB).decrypt(t)

def FOne(x, y):
  return xor(AESE(y, x), y)

def FTwo(x, y):
  return xor(AESE(x, x), y)

def DoFOne():
  rnd = Random.new()
  x1 = rnd.read(16)
  y1 = rnd.read(16)
  f1 = FOne(x1, y1)

  # <Fill in x2/y2>
  y2 = rnd.read(16)
  x2 = AESD(y2, xor(xor(AESE(y1, x1), y1), y2))

  f2 = FOne(x2, y2)
  print "F1: %r" % (f1 == f2)
  print "x1: %s" % x1.encode("hex")
  print "y1: %s" % y1.encode("hex")
  print "f1: %s" % f1.encode("hex")
  print "x2: %s" % x2.encode("hex")
  print "y2: %s" % y2.encode("hex")
  print "f2: %s" % f2.encode("hex")
  print ""

def DoFTwo():
  rnd = Random.new()
  x1 = rnd.read(16)
  y1 = rnd.read(16)
  f1 = FTwo(x1, y1)

  # <Fill in x2/y2>
  x2 = rnd.read(16)
  y2 = xor(xor(AESE(x1, x1), AESE(x2, x2)), y1)

  f2 = FTwo(x2, y2)
  print "F2: %r" % (f1 == f2)
  print "x1: %s" % x1.encode("hex")
  print "y1: %s" % y1.encode("hex")
  print "f1: %s" % f1.encode("hex")
  print "x2: %s" % x2.encode("hex")
  print "y2: %s" % y2.encode("hex")
  print "f2: %s" % f2.encode("hex")
  print ""

if __name__ == "__main__":
  DoFOne()
  DoFTwo()
