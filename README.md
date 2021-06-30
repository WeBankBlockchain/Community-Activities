# Community-Activities

微众银行区块链具有完备的社区活动体系，例如举办区块链开发者大赛、区块链技术黑客马拉松等赛事，联合高校开展课题研究和课程合作，培育区块链专业人才。本仓库将汇聚社区活动成果，将获奖作品、团队项目分享给社区开发者，以促进交流与项目完善。

## 微众银行×华南理工大学：2021年第三届“区块链”实训课

### 课程简介

《基于分布式账本和区块链的应用实训》是微众银行和华南理工大学共建的2021年教育部产学合作协同育人示范课，学生在实训过程中，通过学习微众银行一系列开箱即用的区块链技术项目，上手实践企业级区块链应用开发。

课程内容由微众银行区块链专家团队制定并讲授，涵盖FISCO BCOS基础功能和实操、智能合约开发实操、智能合约编程语言liquid开发实操、WeCross跨链实操、多方大数据隐私计算平台WeDPR-PPC等。

实训课程采用分布式协同模式，学生组成若干项目小组，协同完成区块链应用开发。从需求分析到代码实现，再到项目路演答辩，实训课程的每个环节尽可能还原真实职场工作流程，

### **项目作业说明**

##### 1、项目作业题目

设计一个分布式健康码互认平台，分布式健康码互认平台可包含如下角色和功能：

* 用户：上传用户信息（姓名，身份证号，居住地），向商场出示健康码

* 医院：校验用户信息，上传核酸检测信息（检测时间，检测结果）

* 政府：上传地区安全信息（地区是否为高危区域）

* 商场：校验用户出示的健康码是否有效，是否允许用户进入商场

##### 2、团队分组建议

每组的角色包括产品经理、开发和运维，建议角色：

* 产品经理：负责需求分析和产品设计；

* 开发人员：负责编码实现；搭建和维护环境

* 运维人员：负责搭链和维护环境；

* 项目管理：负责项目管理和组织。

##### 3、项目作业评分参考需求

* 区块链搭建：FISCO BCOS底层节点搭建。多机构角色、多群组加分

* 智能合约：智能合约业务代码

* 业务逻辑：应用层业务代码，JAVA端代码

* 前端设计：前端展示代码

* 测试项：包括代码检测、单元测试、自动化测试，监控等

* 加分项：设计场景，参加比赛并提交代码

### 团队作品

##### 1、作业提交说明

团队可按照[PR流程](https://mp.weixin.qq.com/s/Uq5r1IaZfelWnhCThHSKXw)将包含项目介绍和代码的作业提交至本仓库，提交内容说明如下：

* 在本目录创建文件夹，文件夹中用于展示项目介绍、代码等。

* 如果需要提交图片或大于500KB文件，请使用[git-lfs](https://git-lfs.github.com/)，由于git-lfs等组件对中文支持不友好，文件夹和文件名请使用英文。

* 参考格式：项目简介、项目背景、项目目标、方案等。

* 团队可以在各自子目录`Contrcts`目录下提交Solidity合约。合约接口需要有说明文档，要求提供通过[console](https://github.com/FISCO-BCOS/console)、[nodejs-sdk](https://github.com/FISCO-BCOS/nodejs-sdk)或[python-sdk](https://github.com/FISCO-BCOS/python-sdk)调用的操作截图。

* 团队可以在各自子目录`Project`目录下提交项目代码。项目代码要求提供运行指引且需要经过微众银行区块链团队审核。项目代码要求安装步骤清晰、保证能运行和项目干净清晰。

##### 2、团队作品示例

###### [Bithacks团队：DeFiDefender](https://github.com/FISCO-BCOS/hackathon/blob/master/201908-Shenzhen/Bithacks/README.md)

本项目希望在确保平台数据及用户隐私安全的前提下，通过联盟链、分布式身份、密文云存储、隐私计算和联邦学习等多项技术，将数据主权归还给用户，并通过统一的身份标识符，在数据验证和计算层面实现借贷行业的互联互通，为各平台提供贷前身份认证(防范虚假信息和身份冒用)、反欺诈(反多头借贷、行业黑名单)、可信数据溯源(欺诈实体追溯、催收)、AI风险定价等风控服务。



