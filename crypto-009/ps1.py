#!/usr/bin/env python

import sys

# str(bytearray(foo.decode('hex'))) == foo.decode('hex')

#MSGS = ( ---  11 secret messages  --- )

MSGS = ('aasuthsathuseahtuaotuashu', 'basuhasteus', 'casthuseau')

def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])

def random(size=16):
    return open("/dev/urandom").read(size)

def encrypt(key, msg):
    c = strxor(key, msg)
    print c.encode('hex')
    return c

def main():
    key = random(1024)
    ciphertexts = [encrypt(key, msg) for msg in MSGS]


#main()

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

key = bytearray('?' * 1024)

plaintext = [''] * len(ciphertext)
for i in range(len(ciphertext)):
    plaintext[i] = bytearray(len(ciphertext[i]))

# xor all the strings against each other and look for space characters
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

# Initialize all plaintext to be the space character
plaintext = [''] * len(ciphertext)
for i in range(len(ciphertext)):
    plaintext[i] = bytearray(' ' * len(ciphertext[i]))

print 'A'

for i in range(10):
    print len(str(ciphertext[i]))

print 'B'
for i in range(len(ciphertext)):
    print i
    for j in range(len(ciphertext)):
        if (i == j):
            continue

        s = strxor(str(ciphertext[i]), str(ciphertext[j]))

        for k in range(len(s)):
            if not s[k].isalpha() and s[k] != chr(0):
                plaintext[i][k] = '?'

print 'C'

# Print out the plaintexts.
# Should have the spaces decoded.
for i in range(10):
    print "plaintext[%s]: %s" % (i, str(plaintext[i]))

#sys.exit()

print str(plaintext[0])
print str(plaintext[0][0])
print str(plaintext[0][1])
print str(plaintext[0][5]), chr(plaintext[0][5])
print str(plaintext[0][6]), plaintext[0][6]
assert chr(plaintext[0][5]) == '?'
assert chr(plaintext[0][6]) == ' '

print "key"
print key

# Go through all the plaintexts and get the key value for message that have space.
for i in range(10):
    for j in range(len(plaintext[i])):
        if chr(plaintext[i][j]) == ' ':
            c = strxor(chr(ciphertext[i][j]), ' ')
            key[j] = c

print "key"
print key

for i in range(10):
    s = strxor(str(key), str(ciphertext[i]))

# Add guesses to the key here.
#
# m ^ k = c
# k = c ^ m
key[10] = strxor(chr(ciphertext[0][10]), 't')
key[14] = strxor(chr(ciphertext[0][14]), 't')
key[18] = strxor(chr(ciphertext[0][18]), 'n')

key[2] = strxor(chr(ciphertext[2][2]), 'e')

key[35] = strxor(chr(ciphertext[3][35]), 'n')
key[36] = strxor(chr(ciphertext[3][36]), 'c')
key[38] = strxor(chr(ciphertext[3][38]), 'y')
key[39] = strxor(chr(ciphertext[3][39]), 'p')
key[40] = strxor(chr(ciphertext[3][40]), 't')
key[41] = strxor(chr(ciphertext[3][41]), 'i')
key[42] = strxor(chr(ciphertext[3][42]), 'o')
key[50] = strxor(chr(ciphertext[3][50]), 'i')
key[81] = strxor(chr(ciphertext[3][81]), 't')

key[101] = strxor(chr(ciphertext[3][101]), 'n')
key[102] = strxor(chr(ciphertext[3][102]), 'g')
key[103] = strxor(chr(ciphertext[3][103]), ' ')
key[109] = strxor(chr(ciphertext[3][109]), 'p')
key[110] = strxor(chr(ciphertext[3][110]), 't')
key[113] = strxor(chr(ciphertext[3][113]), 'n')
key[115] = strxor(chr(ciphertext[3][115]), 'a')
key[118] = strxor(chr(ciphertext[3][118]), 'o')
key[119] = strxor(chr(ciphertext[3][119]), 'r')
key[122] = strxor(chr(ciphertext[3][122]), 'h')
key[124] = strxor(chr(ciphertext[3][124]), ' ')

key[54] = strxor(chr(ciphertext[4][54]), ' ')
key[56] = strxor(chr(ciphertext[4][56]), 'p')

key[7] = strxor(chr(ciphertext[5][7]), 'r')
key[30] = strxor(chr(ciphertext[5][30]), 'r')
key[31] = strxor(chr(ciphertext[5][31]), 'a')
key[32] = strxor(chr(ciphertext[5][32]), 'p')
key[33] = strxor(chr(ciphertext[5][33]), 'h')
key[34] = strxor(chr(ciphertext[5][34]), 'y')
key[86] = strxor(chr(ciphertext[5][86]), 'l')
key[92] = strxor(chr(ciphertext[5][92]), 't')
key[93] = strxor(chr(ciphertext[5][93]), 'e')
key[94] = strxor(chr(ciphertext[5][94]), 'r')
key[99] = strxor(chr(ciphertext[5][99]), 'd')
key[125] = strxor(chr(ciphertext[5][125]), 'r')
key[131] = strxor(chr(ciphertext[5][131]), 'a')

key[82] = strxor(chr(ciphertext[6][82]), 'r')
key[83] = strxor(chr(ciphertext[6][83]), 'c')
key[84] = strxor(chr(ciphertext[6][84]), 'e')

key[95] = strxor(chr(ciphertext[7][95]), 'r')

key[25] = strxor(chr(ciphertext[8][25]), 'o')
key[26] = strxor(chr(ciphertext[8][26]), 'n')

print "key"
print key

# See changes.
for i in range(10):
    print i, strxor(str(key), str(ciphertext[i]))

print "target", strxor(str(key), str(target_ciphertext))
