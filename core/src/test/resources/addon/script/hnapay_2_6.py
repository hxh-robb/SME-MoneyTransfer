from datetime import datetime,timedelta
from ft.addon.PythonAddon import SSH
import time, os

def sign(param):
    #print((SSH.clone('/tmp/test.txt')))
    result = {}

    account_dict = param.account.fields
    task = param.task
    amount_in_cents = task.amount * 100

    ## fixed
    result['version'] = '2.6'
    result['type'] = '1000'
    result['buyerMarked'] = ''
    result['payType'] = 'ALL'
    result['orgCode'] = ''
    result['currencyCode'] = '1'
    result['directFlag'] = '0'
    result['borrowingMarked'] = '0'
    result['couponFlag'] = '0'
    result['remark'] = ''
    result['charset'] = '1'
    result['signType'] = '1'

    ## static generated
    result['customerIP'] = account_dict.get('customerIP','')
    result['returnUrl'] = account_dict.get('returnUrl','')
    result['noticeUrl'] = account_dict.get('noticeUrl','') + '/' + param.account.id
    result['partnerID'] = account_dict.get('partnerID','')

    ## dynamic generated
    local_now = datetime.now()
    result['serialID'] = int(time.mktime(local_now.timetuple())*1e3) + int(local_now.microsecond/1e3)
    result['submitTime'] = local_now.strftime('%Y%m%d%H%M%S')
    result['orderDetails']=','.join(map(str,(task.id, amount_in_cents, account_dict.get('displayName',''), account_dict.get('goodsName','GOOD'), 1)))
    result['totalAmount'] = amount_in_cents
    result['platformID'] = task.platform

    ## dynamic calculated
    result['failureTime'] = (local_now + timedelta(days=account_dict.get('failureTime', 90))).strftime('%Y%m%d%H%M%S')
    result['signMsg'] = 'TODO' #account_dict['cert']

    return result

def verify():
    pass
