# SME-MTS(SME Money Transfer Service) - SME金流服务

[![Telegram Chat](https://cdn.rawgit.com/Patrolavia/telegram-badge/8fe3382b/chat.svg)](https://t.me/joinchat/EqTEwQ0QTWL0kbXYUoTbfA)

## 项目简介

[金流服务][1]是一种简化商户接入电子支付平台的支付网关，通过适配接口的方式去除各类电子支付平台的API差异，为商户提供统一简化的对接API，籍此降低商户接入支付平台的难度。商户通过金流服务可快速为客户建立多种支付方式，从而提高交易效率加快商务发展。

SME金流服务(SME-MTS)是一套基于springboot框架的微服务实现。通过API接口，商户可接入SME-MTS为客户提供多种电子支付方式。SME-MTS并不提供客户资金管理及前端解决方案，也不提供接入电子支付平台的商务性资源，因此接入本服务的电商平台须具备管理客户资金的模块，并准备电子支付平台的接入账户资源。

## 项目使命

![sme-mts-mission](docs/pics/sme-mts-mission.png)

该项目的目标是建立一个性能优良，安全可靠且易于对接的支付网关，使电商平台可方便的接入基数庞大的金融机构支付平台，迅速开展支付业务。同时通过实时分析各个资金渠道的运行质量及资费情况智能选择出当下性价比最优的资金通道，为合作方节省金流成本。

本项目遵循dev/ops实践及微服务架构设计，期望籍此建立一套高可维护，稳定可靠且易于扩容的企业级应用。

## 前置需求

TODO

## 入门指南

### 开发环境部署

* TODO

### 生产环境部署

* TODO

## 功能特性

<details>
<summary><strong>第一阶段(Deadline:2018-07-31)</strong></summary>

<ul>
  <li>基础架构设计
    <ul>
      <li>程序框架(SpringBoot)</li>
      <li>应用容器化(Docker)</li>
      <li>自动构建(Jenkins)</li>
    </ul>
  </li>
  <li>支撑兄弟项目Demo版本功能
    <ul>
      <li>客户转账充值
        <ul>
          <li>管理人员手工加币</li>
          <li>模拟自动加币(附言尾数确定自动上分成功与否:奇数掉单,偶数成功,延迟30s)</li>
        </ul>
      </li>
      <li>客户提款
        <ul>
          <li>资金冻结后添加出款任务</li>
          <li>管理人员审批出款任务</li>
          <li>模拟自动出款(延迟30s)</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>

</details>

<details>
<summary><strong>第二阶段</strong></summary>

<ul>
  <li>TODO</li>
</ul>

</details>

## API

* TODO

## 支持人员

* Robb(robb@smeinternet.com)

<!---
* Novia(novia@smeinternet.com)
--->

[1]:https://atm60000.com/%E9%9B%BB%E5%95%86%E4%B8%AD%E8%AC%9B%E7%9A%84%E3%80%8C%E9%87%91%E6%B5%81%E3%80%8D%E6%98%AF%E4%BB%80%E9%BA%BC%EF%BC%9F%E9%87%91%E6%B5%81%E7%99%BD%E8%A9%B1%E6%96%87%E8%A7%A3%E9%87%8B%E8%88%87%E6%8E%A8/
