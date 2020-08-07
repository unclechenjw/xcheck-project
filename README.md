# Xcheck

#### 在 Web 开发中, 我们经常需要校验各种参数，这是一件繁琐又重要的事情，对于很多人来说，在做参数校验的时候，会有以下几种类型的处理方式。
1. 甩锅型，校验太麻烦了，让客户端去负责校验就行了，调用方传错了是调用方的问题，不是服务的问题，甩个 500 错误让他们好好反省
2. 劳模型，有多少参数，我就写多少个 if 语句做判断，校验不通过的都写一句友好的提示，如：“用户名不能为空”，“电话不能为空”...
3. 工具型，自己写个参数校验的通用工具，然后每个请求接收到的参数都调用工具方法来校验，校验不通过就把校验结果返回给调用方
4. 半自动型，对SpringMVC了解朋友都知道，它支持 Bean Validation，因此可以通过使用javax.validation.constraints包下的注解，如@NotNull@Max@Min等，来实现由框架处理数据校验。
    - 但也需要写各个字段注解配置，不同异常拦截配置BindException、MethodArgumentNotValidException、ConstraintViolationException等等，冗余代码还是有点多

### xcheck提供简单语法进行参数校验，支持form表单、json、query param参数校验


## 使用说明

### 校验语法规则
 * 参数格式: 字段名@验证方法缩写名(参数)
 * > 普通校验 filed1@d@l(1)  field必须1位数字
 * > 多参校验 [filed1,field2]@d@ml(10)  field不能为空必须为数字长度最大10位
 * > 如验证字段是否全字母 ：Field@w
 * 验证字段长度1至3位： Field@l(1,3) 最大长度10位Field@ML_10
 * @w:word      是否全字母
 * @W:non-word  是否全非字母
 * @d:digit     是否全数字
 * @D:non-digit 是否全非数字
 * @l(1): length 长度是否为1位
 * @l(1,5):     长度是否在指定范围内1至5位
 * @ml(5): max_length 长度小于等于5位
 * @e:Email     是否邮箱地址格式
 * @p:phone     是否11位有效手机号码
 * @$: money    金额格式
 * @in:         字段必须参数范围内  example： Field@IN_a,b,c Field只能为a,b或c
 * @if          example if('conditon1','conditon2','conditon3')
 * @reg regEx   正则表达式校验
 * : 校验公式冒号后':'可以添加错误提示内容,无则使用系统默认提示
 *
 * ###########################################
 * 字段可空或适应某校验规则请替换@为#调用校验方法
 * 注意！ 以上所有@校验方法 ，#全部支持
 * 例如：验证字段为空或者全字母 ：Field#w
 * #w:     字符为空或者字母
 * #ML(10)  字符为空或者最大长度为10位
 *
 * 数值或日期字段之间逻辑比较运算
 * 例如： >、>=、<、<=、==与!=
 * example1: field1<field2 字段1小于字段2
 * example2: field<100     字段小于指定值100

> 详细使用方法请参考sample项目样例
