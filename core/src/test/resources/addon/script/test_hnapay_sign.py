from java.io import File,FileInputStream
from java.security import KeyStore,Signature
from ft.addon.PythonAddon import SSH

def test(param):
  #param=dict(map(lambda x:(str(x[0]),x[1]),param.iteritems()))

  jks_path=SSH.cloneAndGetLocalPath(param['jks'])
  jks_password=param['jksPassword']
  key_alias=param['keyAlias']
  key_password=param['keyPassword']
  hash_mac=hnapay_mac(param['data'])

  fis=FileInputStream(jks_path)
  jks=KeyStore.getInstance('JKS')
  jks.load(fis, list(jks_password))
  fis.close()
  sign_util=Signature.getInstance('SHA1withRSA')
  sign_util.initSign(jks.getKey(key_alias, list(key_password)))
  sign_util.update(bytearray(map(ord, hash_mac.encode('utf-8'))))
  signature=''.join(map(lambda c: '{:02x}'.format((c+1)*-1^0xFF) if c < 0 else '{:02x}'.format(c), sign_util.sign()))

  # data=param['data']
  return {'data':param['data'], 'signature':signature}

def hnapay_mac(plain):
  three_quarters = 24
  one_eighth = 4
  high_bits = (0xffffffff << (32 - 4)) & 0xffffffff
  hash_value = 0
  for char in plain:
    hash_value = ( hash_value << one_eighth) + ord(char)
    i = hash_value & 0xF0000000
    if i != 0:
      j=i >> three_quarters
      j = (0xFFFFFF00 | j) if j >= 0x80 else j # signed shift right
      hash_value = ( hash_value ^ j)
      hash_value = hash_value & ~high_bits
  return str(hash_value & 0x7fffffff)