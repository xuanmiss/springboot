# Redis-Springboot
####~~author miss~~
==~~操作说明不包含使用原理和介绍~~==

新建springboot项目或者module

引入maven依赖

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

引入redis-springboot依赖后，就能在项目中使用redis了。操作redis的方式有很多种。Springboot配合使用==RedisTemplate==

首先在Springboot配置文件加入Redis配置

```
spring.redis.database=0

spring.redis.host=127.0.01

spring.redis.port=7000

spring.redis.password=

spring.redis.pool.max-active=5

spring.redis.pool.max-wait=-1

spring.redis.pool.max-idle=5

spring.redis.pool.min-idle=0

spring.redis.timeout=30000
```
以上配置根据实际环境配置，主要配置host，port

新建RedisConfig类，配置RedisTemplate


```
@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();
        template.setConnectionFactory(factory);
        template.setHashKeySerializer(keySerializer);
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }
```
@Bean将RedisTemplate注入Spring。可以在项目中直接装配(@Autowired)使用。

RedisTemplate操作redis中的数据结构有以下方式：
- redisTemplate.opsForValue();//操作字符串
- redisTemplate.opsForHash();//操作hash
- redisTemplate.opsForList();//操作list
- redisTemplate.opsForSet();//操作set
- redisTemplate.opsForZSet();//操作有序set

> ==如果操作String类型的数据，最好使用StringRedisTemplate==

#### String 操作
使用时注入


```
@Autowired

private StringRedisTemplate stringRedisTemplate;
```


使用RedisTemplate操作如下：
- set void set(K key, V value);
==*写入数据*==
```
stringRedisTemplate.opsForValue().set("define","mpass");
```

- set void set(K key, V value, long timeout, TimeUnit unit);
==*写入带过期时间的数据*==
```
stringRedisTemplate.opsForValue().set("define", "definesysPass",60*10, TimeUnit.SECONDS);
//向redis里存入数据和设置缓存时间,并指定时间类型
```

- set void set(K key, V value, long offset);
==*从偏移量（offset)开始覆盖数据*==
```
stringRedisTemplate.opsForValue().set("hello","definesys");
stringRedisTemplate.opsForValue().set("hello","pass",6);
logger.info(stringRedisTemplate.opsForValue().get("hello"));
//结果：INFO c.e.redisspringboot.RedisTemTest - definepass
```

- setIfAbsent Boolean setIfAbsent(K key, V value);
==*如果key不存在，写入数据并返回true,否则不覆盖并返回false*==
```
String key = "hello";
String value = "define";
Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,value);
if(flag)
{
    logger.info("key***"+key+"不存在");
}else{
    logger.info("key***"+key+"已存在，不覆盖");
}
logger.info(stringRedisTemplate.opsForValue().get(key));

结果1(key=hello):
INFO c.e.redisspringboot.RedisTemTest  key***hello已存在，不覆盖
INFO c.e.redisspringboot.RedisTemTest - definepass
结果2(key=hello1):
INFO c.e.redisspringboot.RedisTemTest - key***hello1不存在
INFO c.e.redisspringboot.RedisTemTest - define
```

- get V get(Object key);
==*取出key对应的数据*==
```
String result = stringRedisTemplate.opsForValue().get("hello");
```

- getAndSet V getAndSet(K key, V value);
```
String re = stringRedisTemplate.opsForValue().getAndSet("hello1","newDefine");
logger.info(re);
logger.info(stringRedisTemplate.opsForValue().get("hello1"));

结果：
INFO c.e.redisspringboot.RedisTemTest - define
INFO c.e.redisspringboot.RedisTemTest - newDefine
```
- increment Long increment(K key, long delta);
==*为key的数据增加指定的数字，支持整数和浮点数*==

```
stringRedisTemplate.opsForValue().increment("number",10.2);
logger.info(stringRedisTemplate.opsForValue().get("number"));

结果：
INFO c.e.redisspringboot.RedisTemTest - 22.2
//初始为10如果key不存在，redis会先创建赋值0然后执行
```

- append Integer append(K key, String value);
==*如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾并返回字符串长度。如果键不存在，则它被创建并设置为空字符串，然后执行追加*==
```
Integer l= stringRedisTemplate.opsForValue().append("new","hello ");
logger.info("长度是：***"+l+"***"+stringRedisTemplate.opsForValue().get("new"));
Integer ll= stringRedisTemplate.opsForValue().append("new","World");
logger.info("长度是：***"+ll+"***"+stringRedisTemplate.opsForValue().get("new"));

结果：
INFO c.e.redisspringboot.RedisTemTest - 长度是：***6***hello 
INFO c.e.redisspringboot.RedisTemTest - 长度是：***11***hello World
```

- get String get(K key, long start, long end);
==*获取key对应字符串中的某一部分*==
```
String key = "hello";//value = definepass
logger.info(stringRedisTemplate.opsForValue().get(key,0,5));
logger.info(stringRedisTemplate.opsForValue().get(key,0,-1));
logger.info(stringRedisTemplate.opsForValue().get(key,-4,-1));

结果：
INFO c.e.redisspringboot.RedisTemTest - define
INFO c.e.redisspringboot.RedisTemTest - definepass
INFO c.e.redisspringboot.RedisTemTest - pass
```

- size Long size(K key);
==*返回key所对应的value的长度*==

```
Long l = stringRedisTemplate.opsForValue().size("hello");
logger.info(l.toString());

结果：
INFO c.e.redisspringboot.RedisTemTest - 10
```

#### List操作
使用时注入


```
@Autowired

private RedisTemplate redisTemplate;
```


使用RedisTemplate操作如下：

- Long leftPush(K key, V value);
==*将所指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表*==
```
Long l =redisTemplate.opsForList().leftPush("user","bb");
logger.info(l.toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - 2
```

- Long leftPush(K key, V value , V1 value);
==*将V1插入到V的左边*==
```
logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
redisTemplate.opsForList().leftPush("newList","2","4");
logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - [3, 2, 1]
INFO c.e.redisspringboot.RedisListTemTest - [3, 4, 2, 1]
```
- Long leftPushAll(K key, V... values);
==*把一个数组/List插入到列表头部*==
```
String [] users = new String[]{"aa","bb","cc"};
List<String> userlist = new ArrayList<String>();
userlist.add("dd");
userlist.add("ee");
logger.info(redisTemplate.opsForList().leftPushAll("user",users).toString());
logger.info(redisTemplate.opsForList().leftPushAll("user",userlist).toString());
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - 3
INFO c.e.redisspringboot.RedisListTemTest - 5
INFO c.e.redisspringboot.RedisListTemTest - [ee, dd, cc, bb, aa]
```

- List<V> range(K key, long start, long end);
==*返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素*==
```
List<String> rs = redisTemplate.opsForList().range("user",0,-1);
for(String s : rs)
{
    logger.info(s);
}
结果：
INFO c.e.redisspringboot.RedisListTemTest - ee
INFO c.e.redisspringboot.RedisListTemTest - dd
INFO c.e.redisspringboot.RedisListTemTest - cc
INFO c.e.redisspringboot.RedisListTemTest - bb
INFO c.e.redisspringboot.RedisListTemTest - aa
```

- void trim(K key, long start, long end);
==*修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引*==
```
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());
redisTemplate.opsForList().trim("user",1,-1);//裁剪第一个元素
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - [ee, dd, cc, bb, aa]
INFO c.e.redisspringboot.RedisListTemTest - [dd, cc, bb, aa]
```

- Long leftPushIfPresent(K key, V value);
==*只有存在key对应的列表才能将这个value值插入到key所对应的列表中*==
```
logger.info(redisTemplate.opsForList().leftPushIfPresent("notPresent","firstValue").toString());
logger.info(redisTemplate.opsForList().leftPush("notPresent","secondValue").toString());
logger.info(redisTemplate.opsForList().leftPushIfPresent("notPresent","thirdValue").toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - 0
INFO c.e.redisspringboot.RedisListTemTest - 1
INFO c.e.redisspringboot.RedisListTemTest - 2
```
- void set(K key, long index, V value);
==*设置key列表中index位置的值为value*==
```
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());
redisTemplate.opsForList().set("user",1,"newValue");
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - [dd, cc, bb, aa]
INFO c.e.redisspringboot.RedisListTemTest - [dd, newValue, bb, aa]
```

- V leftPop(K key);
==*弹出最左边的元素，弹出之后该值在列表中将不复存在*==
```
Object rs = redisTemplate.opsForList().leftPop("user");
logger.info(rs.toString());
logger.info(redisTemplate.opsForList().range("user",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - dd
INFO c.e.redisspringboot.RedisListTemTest - [newValue, bb, aa]
```
- V leftPop(K key, long timeout, TimeUnit unit);
==*移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止*==
```
//和leftPop用法一致
```

-Long remove(K key, long count, Object value);
==*从存储在键中的列表中删除等于值的元素的第一个计数事件。
计数参数以下列方式影响操作：*==
1. count> 0：删除等于从头到尾移动的值的元素。
1. count <0：删除等于从尾到头移动的值的元素。
1. count = 0：删除等于value的所有元素
```
logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());
redisTemplate.opsForList().remove("newList",0,"2");
logger.info(redisTemplate.opsForList().range("newList",0,-1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - [2, 3, 4, 2, 1]
INFO c.e.redisspringboot.RedisListTemTest - [3, 4, 1]
```

- V index(K key, long index);
==*根据下标获取列表中的值*==
```
logger.info(redisTemplate.opsForList().index("newList",1).toString());

结果：
INFO c.e.redisspringboot.RedisListTemTest - 4
```
--- 

以上是使用客户端api或者Redis模板进行数据操作。
下面是将redis和springboot的缓存相结合
### Spring Cache 和 Redis
Spring 提供了注解的方式来支持缓存
- @CachePut
==*@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中*==
```
@CachePut(value = "user", key = "#root.targetClass + #user.name")
public User addUser(User user)
{
     userMapper.addUser(user);
     return userMapper.getUserByName(user.getName());
}
```
增加的人员信息会直接缓存进Redis一份。

1. value: Cache的名称
2. key:Redis中数据保存的键

- @Cacheable
==*被此注解标记的方法，Spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法*==
```
@Cacheable(value = "user", key = "#root.targetClass + #name", unless = "#result eq null")
public User getUserByName(String name)
{
    logger.info("从数据库取第一次的数据");
    return userMapper.getUserByName(name);
}
```
1. value: Cache的名称
2. key:Redis中数据的键
3. ubless:缓存条件，只有当满足此条件才不进行缓存
4. condition：当满足条件才进行缓存(condition="#name.length>0")

- @CacheEvict
==* @CacheEvict是用来标注在需要清除缓存元素的方法上*==
```
@CacheEvict(value = "user", key = "#root.targetClass + #name")
public void delUserByName(String name)
{
    userMapper.delUserByName(name);
}
```
@CacheEvict比@Cacheable多了两个属性
1. beforeInvocation属性，默认false,表示在方法执行成功后触发清除操作。当为true是，则在方法执行前触发
2. allEntries属性，表示是否需要清除缓存中的所有元素。默认为false
```
@CacheEvict(value = "user",allEntries = true)
public void clearAll()
{

    new Thread(()->{
            RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
            jedis.subscribe(listener,"__keyevent@0__:del");
    }).start();
    logger.debug("**************************正在清除缓存 user");
}
```
以上




