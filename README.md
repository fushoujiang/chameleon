# chameleon
Chameleon/kəˈmiːlɪən/，简写CML，中文名卡梅龙；中文意思变色龙
可以通过该项目实现对项目运行请求搜集，来动态调整线程池、限流器等参数。
chameleon-core：动态化核心代码
chameleon-datasource可变化数据源，可以接配置中心，或者mysql等
chameleon-lang：工具包
chameleon-limit：动态限流组件，需依赖数据源
chameleon-threadpool：动态线程池，需要依赖数据源
chameleon-lock：动态锁：可以更改配置