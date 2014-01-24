#!/usr/bin/env python

import math
import os
from binascii import hexlify, unhexlify
from Crypto.Hash import SHA256


def hash(filename):
    assert os.path.exists(filename)
    # print "file name:", filename

    size = os.stat(filename).st_size
    # print "file size:", size

    BLOCK_SIZE = 1024
    num_blocks = int(math.ceil(float(size) / BLOCK_SIZE))
    # print "num_blocks:", num_blocks

    f = open(filename, 'rb')
    result = ''
    for block in range(num_blocks - 1, -1, -1):
        f.seek(block * BLOCK_SIZE)
        bytes = f.read(BLOCK_SIZE)
        bytes += result
        h = SHA256.new()
        h.update(bytes)
        result = h.digest()
    f.close()

    return result


f = '6 - 2 - Generic birthday attack (16 min).mp4'
print f
h = hash(f)
print hexlify(h)
assert hexlify(h) == '03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8'

f = '6 - 1 - Introduction (11 min).mp4'
print f
h = hash(f)
print hexlify(h)
