# 跨境产品关键运输步骤追溯平台

## 背景介绍
本跨境产品关键运输步骤追溯平台可以提供一个由消费者，商品厂家和商品运输途中所经过的港口这三方参与的信息共享平台。消费者在系统中可以进行注册用户、查询商品信息的操作。厂家可以进行注册商家、生成产品唯一索引、上传产品出厂信息的操作。各港口可以进行上传产品到达此处与离开此处的时间。本系统后台是基于FISCO-BCOS的区块链系统，在保证稳定性和高效性的同时能够提供足够的安全性，可以保护消费者和厂家的隐私和信息的安全性。

### 安装说明
下载后端代码后用 IntelliJ IDEA CE 软件打开整个文件夹
* 注意！！ 在打开之前，需要修改文件夹的名称为fisco-app。 
* 如果jdk版本不匹配的话需要手动配置，具体方法是在Run/DebugConfigurations标签栏中选择JDK11的选项。
* 在运行项目之前，需要启动部署在本机的四节点联盟链，并将node/127.0.0.1/sdk下的证书复制到/src/main/resources/conf目录下。完成复制后，选择运行项目，即可部署后端服务器

下载前端代码后用VS Code打开整个文件夹，需要安装live server 插件。安装完后点击右下角go alive插件即可启动前端

### 页面介绍
* 所有的功能都在页面上方的功能栏中
* 只有完成登录之后才能使用功能，没有账户请先点击注册按钮进行注册
* 登录后可以使用其余五个界面的功能，每种用户有不同的用户权限

### 智能合约
智能合约的内容在根目录下TestContract.sol文件中


# 数据库接口

## U.用户部分

### U1.买家与卖家注册

```java
function register(string user, string passwd, int user_type) public returns(bool, string)
```

```
输入：用户名，密码，用户类型 0 卖家 2 买家
输出：成功与否,报错信息
```

### U2.港口注册

```java
function registerPort(string user, string passwd, string port_name) public returns(bool, string)
```

```
输入：用户名，密码，港口名称
输出：成功与否，报错信息
```

### U3.登录

```java
function login(string user, string passwd) public returns(bool, string, string, int)
```

```
输入：用户名，密码
输出：成功与否，报错信息，token，用户类型
```

### U4.获取港口名称

```java
function getPortName(string user) public constant returns (bool, string, string)
```

```
输入：用户名
输出：成功与否，报错信息，用户名对应的港口名称
```

  ## C.链表部分

  ### C1.生成新的序列号（id) 

```java
function genId() public returns(bool, string, string)
```

```
输出：成功与否，报错信息，id
```

### C2.绑定商家与商品

```java
function bind2(string id, string user, string token) public returns (bool, string)
```

```
输入：商品id,商家用户名，token
输出：成功与否，报错信息
```

### C3.查询绑定关系

```java
function isBind(string id, string user) public constant returns (bool, string)
```

```
输入：商品id,商家用户名
输出：是否，报错信息
```

  ### C4.查询是否初始化

```java
function isInit(string id) public constant returns (bool, string)
```

  ```json
  输入：商品id
  输出：是否，报错信息
  ```

  ### C5.初始化头节点

```java
function initHead(string id, string time, string location, string user, string token) public returns (bool, string)
```

```
输入：商品id，出厂时间，出厂地点，商家，token
输出：成功与否，报错信息
```

### C6.授权港口

```java
function giveRight(string id, string user, string token) public returns (bool, string)
```

```
输入：商品id,港口用户名，token
输出：成功与否，报错信息
```

### C7.查询授权

```java
function hasRight(string id, string user) public constant returns(bool, string)
```

```
输入：商品id, 港口用户名
输出：是否，报错信息
```

  ### C8.添加节点

  ```java
  function addNode(string id, string user, string in_time, string out_time, string token) public returns (bool,string)
  ```

```
输入：商品id，港口用户名，入港时间，出港时间，token
输出：成功与否，报错信息
```

## Q.查询部分

### Q1.查询物流信息

```java
function showPath(string id) public constant returns (string)
```

```
输入：商品id
输出：“time:String location:String in_time:String out_time:String user_id:String ...”
说明：输出是一个String，包含多个数据，数据间用空格分隔，前两个是出厂时间和出厂地点，之后每三个数据分别代表一条物流记录（入港时间，出港时间，港口号）
     
```

### Q2.查询物流信息

```java
function showPortList() public constant returns (string)
```

```
输出：一个String，格式与Q1相似，都是用空格符分隔数据，数据是当前注册过的港口用户名和名称
```
