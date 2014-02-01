import urllib2
from Crypto.Util import strxor

TARGET = 'http://crypto-class.appspot.com/po?er='


#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
class PaddingOracle(object):
    def query(self, q):
        target = TARGET + urllib2.quote(q)    # Create query URL
        req = urllib2.Request(target)         # Send HTTP request to server
        try:
            f = urllib2.urlopen(req)          # Wait for response
        except urllib2.HTTPError, e:
            # print "We got: %d" % e.code       # Print response code
            if e.code == 404:
                return True  # good padding
            return False  # bad padding


CIPHERTEXT = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4'
IV = CIPHERTEXT[0:32]
C0 = CIPHERTEXT[32:64]
C1 = CIPHERTEXT[64:96]
C2 = CIPHERTEXT[96:128]
assert(CIPHERTEXT == IV + C0 + C1 + C2)

m0 = '00' * 16
m1 = '00' * 16
m2 = '00' * 16

MESSAGE = 'The Magic Words are Squeamish Ossifrage'
# M0 = 'The Magic Words '
# M1 = 'are Squeamish Os'
# M2 = 'sifrage'

po = PaddingOracle()

# https://class.coursera.org/crypto-009/forum/thread?thread_id=660
# letters = "aeorisnt lmdcphbukgyfwjvzxqASERBTMLNPOIDCHGKFJUW.1234567890!Y*@V-ZQX_$#,/+?;^%~=&`\)][:<(>"
letters = ''.join(set(MESSAGE))

# guess m0
for i in range(1, 17):
    for j in letters:
        # g is the byte guess
        g = '{0:02x}'.format(ord(j))
        g = m0[0:(32-(i*2))] + g + m0[(32-((i-1)*2)):32]
        # print g
        assert(len(g) == 32)

        pad = '00' * (16 - i) + '{0:02x}'.format(i) * i
        # print pad
        assert(len(pad) == 32)

        iv = strxor.strxor(strxor.strxor(IV.decode('hex'), g.decode('hex')), pad.decode('hex')).encode('hex')
        assert(len(iv) == 32)

        ciphertext = iv + C0

        status = po.query(ciphertext)

        if status:
            m0 = g
            print m0.decode('hex')
            break

assert m0.decode('hex') == 'The Magic Words '

# guess m1
for i in range(1, 17):
    for j in letters:
        g = '{0:02x}'.format(ord(j))
        g = m1[0:(32-(i*2))] + g + m1[(32-((i-1)*2)):32]
        assert(len(g) == 32)

        pad = '00' * (16 - i) + '{0:02x}'.format(i) * i
        assert(len(pad) == 32)

        c0 = strxor.strxor(strxor.strxor(C0.decode('hex'), g.decode('hex')), pad.decode('hex')).encode('hex')
        ciphertext = IV + c0 + C1

        status = po.query(ciphertext)
        if status:
            m1 = g
            print m0.decode('hex') + m1.decode('hex')
            break

assert m1.decode('hex') == 'are Squeamish Os'


# guess m2
# m2 contains padding which is non alphanumeric
for i in range(1, 17):
    found = False
    for j in range(256):
        g = '{0:02x}'.format(j)
        g = m2[0:(32-(i*2))] + g + m2[(32-((i-1)*2)):32]
        assert(len(g) == 32)

        pad = '00' * (16 - i) + '{0:02x}'.format(i) * i
        assert(len(pad) == 32)

        c1 = strxor.strxor(strxor.strxor(C1.decode('hex'), g.decode('hex')), pad.decode('hex')).encode('hex')
        ciphertext = IV + C0 + c1 + C2

        # Need to handle case when we get the exact padding for last block.
        status = po.query(ciphertext)
        if status or (i > 1 and status is None):
            found = True
            m2 = g
            print m0.decode('hex') + m1.decode('hex') + m2.decode('hex')
            break
    assert found
