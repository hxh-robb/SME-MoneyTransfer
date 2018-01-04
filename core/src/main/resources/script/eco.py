def sign(param):
  result = param.copy()
  result['fromPython']='hello, java'
  return result