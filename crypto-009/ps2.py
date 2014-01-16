#!/usr/bin/env python

import binascii
from binascii import hexlify, unhexlify
from Crypto.Cipher import AES
from Crypto.Util import Counter


# Example usage of Python Crypto library.
# http://www.inconteam.com/software-development/41-encryption/55-aes-test-vectors

# CBC
key = '2b7e151628aed2a6abf7158809cf4f3c'
iv = '000102030405060708090A0B0C0D0E0F'
message = '6bc1bee22e409f96e93d7e117393172a'
ciphertext = '7649abac8119b246cee98e9b12e9197d'

obj = AES.new(binascii.unhexlify(key), AES.MODE_CBC, unhexlify(iv))
c = obj.encrypt(unhexlify(message))
assert hexlify(c) == ciphertext

obj = AES.new(unhexlify(key), AES.MODE_CBC, unhexlify(iv))
m = obj.decrypt(unhexlify(ciphertext))
assert hexlify(m) == message

# Question 1
# CBC
key1 = '140b41b22a29beb4061bda66b6747e14'
ciphertext1 = '4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81'
iv1 = ciphertext1[0:32]
ciphertext1 = ciphertext1[32:]

# Question 2
# CBC
key2 = '140b41b22a29beb4061bda66b6747e14'
ciphertext2 = '5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253'
iv2 = ciphertext2[0:32]
ciphertext2 = ciphertext2[32:]

# Question 3
# CTR
key3 = '36f18357be4dbd77f050515c73fcf9f2'
ciphertext3 = '69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329'
iv3 = ciphertext3[0:32]
ciphertext3 = ciphertext3[32:]

# Question 4
key4 = '36f18357be4dbd77f050515c73fcf9f2'
ciphertext4 = '770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451'
iv4 = ciphertext4[0:32]
ciphertext4 = ciphertext4[32:]


obj1 = AES.new(unhexlify(key1), AES.MODE_CBC, unhexlify(iv1))
m1 = obj1.decrypt(unhexlify(ciphertext1))
print "Message 1:", m1

obj2 = AES.new(unhexlify(key2), AES.MODE_CBC, unhexlify(iv2))
m2 = obj2.decrypt(unhexlify(ciphertext2))
print "Message 2:", m2

ctr3 = Counter.new(128, initial_value=int(iv3, 16))
obj3 = AES.new(unhexlify(key3), AES.MODE_CTR, unhexlify(iv3), counter=ctr3)
m3 = obj3.decrypt(unhexlify(ciphertext3))
print "Message 3:", m3

ctr4 = Counter.new(128, initial_value=int(iv4, 16))
obj4 = AES.new(unhexlify(key4), AES.MODE_CTR, unhexlify(iv4), counter=ctr4)
m4 = obj4.decrypt(unhexlify(ciphertext4))
print "Message 4:", m4
