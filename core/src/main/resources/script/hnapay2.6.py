from datetime import datetime

def sign(static_fields):
    result = {}

    # fixed
    result['version'] = '2.6'
    result['type'] = '1000'

    # dynamic generated
    result['submitTime'] = datetime.now().strftime('%Y%m%d%H%M%S')

    # static generated
    result.update(static_fields)

    return result

def verify():
    pass