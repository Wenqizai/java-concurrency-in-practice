- ReentrantLock

  1. 超时获取, 非阻塞
  2. 可中断的锁等待
  3. 公平性
  4. 非块接口的加锁
  5. 性能略优于synchronized
  6.  ReentrantReadWriteLock
  7. 如果finnaly没有unlock, 将会是灾难式结果

  ```java
  // 未获取到锁
  if (!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS)) {
      return false;
  }
  ```

- synchronized
  1. 使用简单
  2. 自动释放锁, 危险程度低

ReentrantLock不能完全代替synchronized，只有synchronized无法满足需求时，才使用ReentrantLock。