# common-spring-boot-start
    采用Spring-boot-start进行打包，作为一个共有的模块即可自动引入

# Logging
    在controller上中的方法直接@Logging即可进行日志参数打印

# redis
    配置文件中直接配置即可使用，例如：
    spring.redis.host= 127.0.0.1
    spring.redis.port= 6379

# mail
    对邮件发送进行了二次封装，例如：

    mail.port= 465
    mail.email_name= a@qq.com
    mail.email_password= iw078Kz58X6
    mail.mail_host= smtp.mxhichina.com
    mail.to_mail= hua_zhenguo@163.com

# request response
    公有参数请求和公有的返回
# util

## BeanCopyUtil
    主要做实体之间的转换，主要对BeanUtils.copyProperties进行了二次封装
## HttpClient
    是对http请求做了封装，Get和post

## IDGenerate
    是分布式下的id的生成方式

## PageUtil
    是对page做了一个封装




