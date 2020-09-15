lua
 动态语言  效率类似c语言
  lua 语言来高并发
  因为nginx是访问不了 kafka
  但是lua可以访问kafka
  所以在nginx中加上了lua 就可以连接 kafka
  
网关   路由(可以具体到某一台的服务器)   软防火墙  （输出动态页面） 
  如果是用zuul 来做的话，效率很低，远不如c语言。
  网关
  openresty   nginx和lua制作的  防火墙
  kong  
redis 和nginx 都可以支持模块的扩展  