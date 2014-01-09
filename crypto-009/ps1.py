#!/usr/bin/env python

def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])

def encrypt(key, msg):
    c = strxor(key, msg)
    # print c.encode('hex')
    return c

ciphertext = [''] * 10

ciphertext[0] = bytearray('315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e'.decode('hex'))

ciphertext[1] = bytearray('234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f'.decode('hex'))

ciphertext[2] = bytearray('32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb'.decode('hex'))

ciphertext[3] = bytearray('32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa'.decode('hex'))

ciphertext[4] = bytearray('3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba43722130f042f8ec85b7c2070'.decode('hex'))

ciphertext[5] = bytearray('32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d11902aebc66f2b2e436434eacc0aba938220b084800c2ca4e693522643573b2c4ce35050b0cf774201f0fe52ac9f26d71b6cf61a711cc229f77ace7aa88a2f19983122b11be87a59c355d25f8e4'.decode('hex'))

ciphertext[6] = bytearray('32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83414aaaf261bda2e97b170fb5cce2a53e675c154c0d9681596934777e2275b381ce2e40582afe67650b13e72287ff2270abcf73bb028932836fbdecfecee0a3b894473c1bbeb6b4913a536ce4f9b13f1efff71ea313c8661dd9a4ce'.decode('hex'))

ciphertext[7] = bytearray('315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada43d6712150441c2e04f6565517f317da9d3'.decode('hex'))

ciphertext[8] = bytearray('271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a52222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027'.decode('hex'))

ciphertext[9] = bytearray('466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83'.decode('hex'))

target_ciphertext = bytearray('32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904'.decode('hex'))

key = bytearray(' ' * 1024)

plaintext = [''] * len(ciphertext)
for i in range(len(ciphertext)):
    plaintext[i] = bytearray(len(ciphertext[i]))

# XOR each ciphertext to look for space characters.
#
# space = '0x20'
# 0x20 ^ ' ' = 0x0
# 0x20 ^ 'a' = 'A'
# 0x20 ^ 'b' = 'B'
# ...
# 0x20 ^ 'A' = 'a'
# 0x20 ^ 'B' = 'b'
#
# m1 ^ k = c1
# m2 ^ k = c2
# m1 ^ m2 = c1 ^ c2
#
# space XORed with character or space will be a character or NUL.
#
# If c1 ^ c2 is not in NUL, a-z, or A-Z, then neither m1 nor m2 are space characters.
#
# Initialize all plaintext to be the space character
# Then set to non-space if we do not think it's a space.
plaintext = [''] * len(ciphertext)
for i in range(len(ciphertext)):
    plaintext[i] = bytearray(' ' * len(ciphertext[i]))

for i in range(10):
    for j in range(10):
        # Do not check XOR with itself.
        if (i == j):
            continue

        s = strxor(str(ciphertext[i]), str(ciphertext[j]))

        for k in range(len(s)):
            if not s[k].isalpha() and s[k] != chr(0):
                plaintext[i][k] = '?'
                if i == 0 and k == 2 and False:
                   print i, j, k
                   print "?", s[k], "?"
                   assert False

# Print out the plaintexts.
# We should many of the spaces decoded.
for i in range(10):
    print "plaintext[%s]: %s" % (i, str(plaintext[i]))

print

# Go through all the plaintexts.
# For each space we figure out what the key is.
#
# k = c ^ m = c ^ space
known = [False] * 1024
for i in range(10):
    for j in range(len(plaintext[i])):
        if chr(plaintext[i][j]) == ' ':
            c = strxor(chr(ciphertext[i][j]), ' ')
            key[j] = c
            known[j] = True

# Decrypt with the assummed known pieces of the key
# c = k ^ m
# m = c ^ k
# Print out plaintexts
#for i in range(10):
#    print "plaintext[%s]: %s" % (i, strxor(str(key), str(ciphertext[i])))
for i in range(1024):
    if known[i]:
        for j in range(10):
            if i < len(plaintext[j]):
                #print i, j
                plaintext[j][i] = strxor(chr(ciphertext[j][i]), chr(key[i]))

for i in range(10):
    print "plaintext[%s]: %s" % (i, str(plaintext[i]))
print
print
target_plaintext = strxor(str(key), str(target_ciphertext))
print "target", target_plaintext
print

# Add guesses to the key here.
#
# m ^ k = c
# k = c ^ m
#
# Guess the key by guessing the message based on common English patterns.

# Update the key and update the plaintext.
def set_key(position, character):
    key[position] = character
    assert known[position] is False
    known[position] = True
    for i in range(10):
        if position < len(plaintext[i]):
            plaintext[i][position] = strxor(chr(ciphertext[i][position]), character)

set_key(2, strxor(chr(ciphertext[0][2]), ' '))
set_key(10, strxor(chr(ciphertext[0][10]), 't'))
set_key(14, strxor(chr(ciphertext[0][14]), 't'))
set_key(18, strxor(chr(ciphertext[0][18]), 'n'))
set_key(128, strxor(chr(ciphertext[0][128]), 'b'))

set_key(35, strxor(chr(ciphertext[3][35]), 'n'))
set_key(36, strxor(chr(ciphertext[3][36]), 'c'))
set_key(39, strxor(chr(ciphertext[3][39]), 'p'))
set_key(40, strxor(chr(ciphertext[3][40]), 't'))
set_key(41, strxor(chr(ciphertext[3][41]), 'i'))
set_key(42, strxor(chr(ciphertext[3][42]), 'o'))
set_key(50, strxor(chr(ciphertext[3][50]), 'i'))
set_key(81, strxor(chr(ciphertext[3][81]), 't'))

set_key(101, strxor(chr(ciphertext[3][101]), 'n'))
set_key(102, strxor(chr(ciphertext[3][102]), 'g'))
set_key(103, strxor(chr(ciphertext[3][103]), ' '))
set_key(109, strxor(chr(ciphertext[3][109]), 'p'))
set_key(110, strxor(chr(ciphertext[3][110]), 't'))
set_key(113, strxor(chr(ciphertext[3][113]), 'n'))
set_key(115, strxor(chr(ciphertext[3][115]), 'a'))
set_key(118, strxor(chr(ciphertext[3][118]), 'o'))
set_key(119, strxor(chr(ciphertext[3][119]), 'r'))
set_key(122, strxor(chr(ciphertext[3][122]), 'h'))
set_key(124, strxor(chr(ciphertext[3][124]), ' '))

set_key(54, strxor(chr(ciphertext[4][54]), ' '))
set_key(56, strxor(chr(ciphertext[4][56]), 'p'))

set_key(30, strxor(chr(ciphertext[5][30]), 'r'))
set_key(31, strxor(chr(ciphertext[5][31]), 'a'))
set_key(32, strxor(chr(ciphertext[5][32]), 'p'))
set_key(33, strxor(chr(ciphertext[5][33]), 'h'))
set_key(34, strxor(chr(ciphertext[5][34]), 'y'))
set_key(86, strxor(chr(ciphertext[5][86]), 'l'))
set_key(92, strxor(chr(ciphertext[5][92]), 't'))
set_key(93, strxor(chr(ciphertext[5][93]), 'e'))
set_key(94, strxor(chr(ciphertext[5][94]), 'r'))
set_key(99, strxor(chr(ciphertext[5][99]), 'd'))
set_key(125, strxor(chr(ciphertext[5][125]), 'r'))
set_key(131, strxor(chr(ciphertext[5][131]), 'a'))

set_key(82, strxor(chr(ciphertext[6][82]), 'r'))
set_key(83, strxor(chr(ciphertext[6][83]), 'c'))
set_key(84, strxor(chr(ciphertext[6][84]), 'e'))
set_key(135, strxor(chr(ciphertext[6][135]), 'r'))
set_key(137, strxor(chr(ciphertext[6][137]), 'm'))
set_key(138, strxor(chr(ciphertext[6][138]), 'e'))
set_key(140, strxor(chr(ciphertext[6][140]), 't'))
set_key(155, strxor(chr(ciphertext[6][155]), 'f'))
set_key(158, strxor(chr(ciphertext[6][158]), 'c'))
set_key(159, strxor(chr(ciphertext[6][159]), 'e'))
set_key(161, strxor(chr(ciphertext[6][161]), 't'))
set_key(162, strxor(chr(ciphertext[6][162]), 'o'))
set_key(164, strxor(chr(ciphertext[6][164]), 'b'))
set_key(165, strxor(chr(ciphertext[6][165]), 'r'))
set_key(166, strxor(chr(ciphertext[6][166]), 'e'))
set_key(167, strxor(chr(ciphertext[6][167]), 'a'))
set_key(168, strxor(chr(ciphertext[6][168]), 'k'))
set_key(170, strxor(chr(ciphertext[6][170]), 'y'))
set_key(171, strxor(chr(ciphertext[6][171]), 'o'))

set_key(95, strxor(chr(ciphertext[7][95]), 'r'))

set_key(25, strxor(chr(ciphertext[8][25]), 'o'))
set_key(26, strxor(chr(ciphertext[8][26]), 'n'))
set_key(136, strxor(chr(ciphertext[8][136]), 'u'))
set_key(142, strxor(chr(ciphertext[8][142]), 'r'))
set_key(145, strxor(chr(ciphertext[8][145]), 'e'))
set_key(146, strxor(chr(ciphertext[8][146]), 'c'))
set_key(147, strxor(chr(ciphertext[8][147]), 'r'))
set_key(149, strxor(chr(ciphertext[8][149]), 'p'))
set_key(150, strxor(chr(ciphertext[8][150]), 't'))
set_key(151, strxor(chr(ciphertext[8][151]), 'i'))
set_key(152, strxor(chr(ciphertext[8][152]), 'n'))
set_key(153, strxor(chr(ciphertext[8][153]), 'g'))

# Fix incorrect known keys because there was a non-alpha character somewhere in the column.
known[7] = False
set_key(7, strxor(chr(ciphertext[0][7]), 'f'))
known[160] = False
set_key(160, strxor(chr(ciphertext[6][160]), ' '))
known[144] = False
set_key(144, strxor(chr(ciphertext[6][144]), ' '))
known[154] = False
set_key(154, strxor(chr(ciphertext[6][154]), ' '))
known[156] = False
set_key(156, strxor(chr(ciphertext[6][156]), 'o'))
known[157] = False
set_key(157, strxor(chr(ciphertext[6][157]), 'r'))
known[172] = False
set_key(172, strxor(chr(ciphertext[6][172]), 'u'))

# See changes after guesses.
for i in range(10):
     print "plaintext[%s]: %s" % (i, plaintext[i])

target_plaintext = strxor(str(key), str(target_ciphertext))
print "target:", target_plaintext

import sys

# Check if we are right by encrypting solved plaintext to see if we get original cyphertext.
for i in range(10):
    c = encrypt(str(key), str(plaintext[i]))
    #print c.encode('hex')
    #print str(ciphertext[i]).encode('hex')
    if c.encode('hex') != str(ciphertext[i]).encode('hex'):
        print "Incorrect plaintext for ciphertext", i
        s1 = c.encode('hex')
        s2 = str(ciphertext[i]).encode('hex')
        for j in range(len(s1)):
             assert s1[j] == s2[j]
        #    x = c.encode('hex')[j]
        #    y = str(ciphertext[i])[j]
        #    if x != y:
        #        print "position %s mismatch: %s %s" % (j, x, y)
        #        sys.exit()
    else:
        print "plaintext %s is correct" % i
