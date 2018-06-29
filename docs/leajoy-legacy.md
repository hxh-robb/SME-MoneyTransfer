# 友司充提程序跟踪总结

[![Telegram Chat](https://cdn.rawgit.com/Patrolavia/telegram-badge/8fe3382b/chat.svg)](https://t.me/joinchat/EqTEwQ0QTWL0kbXYUoTbfA)

## 项目现况

### 2018-06-28更新

<table style="text-align:center;">
  <thead style="background-color:yellow">
    <tr>
      <td style="padding:5px;">任务事项</td>
      <td style="padding:5px;">已有资源</td>
      <td style="padding:5px;">缺少资源</td>
      <td style="padding:5px;">跟踪备注</td>
      <td style="padding:5px;">预期产出成果</td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="padding:5px;">模块分析</td>
      <td style="padding:5px;">程序代码</td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;">代码走读中</td>
      <td style="padding:5px;">程序设计参考<br>还原数据库表结构<br>第三方对接代码抽取移植</td>
    </tr>
    <tr>
      <td style="padding:5px;">编译构建</td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;">程序代码Maven依赖(*)</td>
      <td style="padding:5px;">已向上级报备</td>
      <td style="padding:5px;">服务war包</td>
    </tr>
    <tr>
      <td style="padding:5px;">部署</td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;">数据库建库脚本(*)</td>
      <td style="padding:5px;">已向上级报备</td>
      <td style="padding:5px;">服务正常启动运行<br>可供外部部件接入测试</td>
    </tr>
    <tr>
      <td style="padding:5px;">功能验证</td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;">第三方平台商户号(测试第三方支付平台对接)<br>银行卡/支付宝/微信帐户(测试玩家充值流程)<br>使用说明文档(部件接入指导)</td>
      <td style="padding:5px;">已向上级报备</td>
      <td style="padding:5px;">测试报告<br>功能说明</td>
    </tr>
    <tr>
      <td style="padding:5px;">产品集成</td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;">接口文档</td>
      <td style="padding:5px;">已向上级报备</td>
      <td style="padding:5px;">部件可接入服务开展充提业务</td>
    </tr>
  </tbody>
</table>
<!---
    <tr>
      <td style="padding:5px;"></td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;"></td>
      <td style="padding:5px;"></td>
    </tr>
--->
(*) = 必要资源

### 项目规模

* 代码量

```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                          1379          32486          41401         173655
JSP                            105            309             35           5253
CSS                              6            790             31           3883
XML                             22             74              9           1473
Maven                            1              7             18            390
HTML                             2              3              0             21
Javascript                       6              0              9             10
-------------------------------------------------------------------------------
SUM:                          1521          33669          41503         184685
-------------------------------------------------------------------------------
```

* 第三方渠道种类(103种)



### 友方协助人员

* None

---

## 模块分析

### 源码目录结构(TODO)

```
pcpay
├── pom.xml (Maven构建配置)
├── src
│   ├── (*)gens (MyBatis自动生成代码?)
│   ├── (*)main (业务逻辑代码?)
│   └── test
└── WebContent
    ├── broken.html
    ├── css
    ├── img
    ├── js
    ├── META-INF
    ├── (*)pay (第三方支付平台跳转页面)
    └── WEB-INF (Serlvet应用部署配置)
```
(*) = 重点目录

### 数据实体

TODO

### 第三方渠道

TODO

---

## 构建及部署

**缺少资源, 任务挂起**

## 功能验证

**缺少资源, 任务挂起**

## 产品集成

**缺少资源, 任务挂起**

